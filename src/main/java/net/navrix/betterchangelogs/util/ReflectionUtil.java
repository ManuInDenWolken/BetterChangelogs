package net.navrix.betterchangelogs.util;

import java.lang.reflect.Field;

public final class ReflectionUtil {

    public static  <T, V> void setAttributeValue(T t, String fieldName, V v) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = t.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(t, v);
    }

}
