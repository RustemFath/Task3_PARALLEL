package ru.mystudy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

public class UtilsInvocationHandler implements InvocationHandler {
    private final Savable obj;
    private Map<UtilsKeyMap, UtilsValueMap> cacheMap = new HashMap<>();

    public UtilsInvocationHandler(Savable obj) {
        this.obj = obj;
        Thread thread = new Thread(this::clearCache);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Method objMethod = getObjMethod(method);
        String methodName = method.getName();

        if (objMethod.isAnnotationPresent(Cache.class)) {
            System.out.println("Cache proxy invoke " + methodName + ", cache count=" + cacheMap.size());

            synchronized (this) {
                UtilsKeyMap keyMap = findKeyMap(method, obj.getSave());
                if (keyMap != null) {
                    if (cacheMap.containsKey(keyMap)) {
                        UtilsValueMap valueMap = cacheMap.get(keyMap);
                        valueMap.updateExpiredTime();
                        return valueMap.getResult();
                    }
                }
            }

            Object objResult = method.invoke(obj, args);
            synchronized (this) {
                cacheMap.put(new UtilsKeyMap(method, obj.getSave()),
                             new UtilsValueMap(objResult, objMethod.getAnnotation(Cache.class).value()));
            }
            return objResult;
        }

        if (objMethod.isAnnotationPresent(Mutator.class)) {
            System.out.println("Mutator proxy invoke " + methodName + ", args=" + Arrays.asList(args));
        }

        return method.invoke(obj, args);
    }

    private Method getObjMethod(Method method) throws NoSuchMethodException {
        Class<?> objClass = obj.getClass();
        return objClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
    }

    private UtilsKeyMap findKeyMap(Method method, Object save) {
        return cacheMap.keySet().stream()
                .filter(k -> k.getMethod() == method && k.getSave().equals(save))
                .findFirst().orElse(null);
    }

    private void clearCache() {
        try {
            while (true) {
                Map<UtilsKeyMap, UtilsValueMap> copyCacheMap = new HashMap<>(cacheMap);
                boolean changed = copyCacheMap.entrySet().removeIf(entry -> entry.getValue().isExpired());
                if (changed) {
                    synchronized (this) {
                        System.out.println("clear cache, count=" + copyCacheMap.size());
                        cacheMap = copyCacheMap;
                    }
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
