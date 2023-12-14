package ru.mystudy;

import java.lang.reflect.Proxy;

public class Utils {
    public static <T> T cache(Savable obj) {
        if (obj == null) {
            return null;
        }

        Class<?> objClass = obj.getClass();
        ClassLoader objClassLoader = objClass.getClassLoader();
        Class<?>[] objInterfaces = objClass.getInterfaces();
        return (T) Proxy.newProxyInstance(objClassLoader, objInterfaces, new UtilsInvocationHandler(obj));
    }
}
