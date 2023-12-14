package ru.mystudy;

import java.lang.reflect.Method;
import java.util.Objects;

public class UtilsKeyMap {
    private final Method method;

    private final Object save;

    public UtilsKeyMap(Method method, Object save) {
        this.method = method;
        this.save = save;
    }

    public Method getMethod() {
        return method;
    }

    public Object getSave() {
        return save;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UtilsKeyMap keyObj = (UtilsKeyMap) o;
        if (!Objects.equals(method, keyObj.method)) return false;
        if (!Objects.equals(save, keyObj.save)) return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int result = method != null ? method.hashCode() : 0;
        result = 31 * result + (save != null ? save.hashCode() : 0);
        return result;
    }
}
