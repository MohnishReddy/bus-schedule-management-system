package com.example.bsms.utils;

import java.lang.reflect.Field;

public class ReflectionUtils {
    public static void mapProperties(Object source, Object destination, boolean ignoreNullValues) throws IllegalArgumentException, IllegalAccessException {
        Class<?> sourceClass = source.getClass();
        Class<?> destinationClass = destination.getClass();

        Field[] sourceFields = sourceClass.getDeclaredFields();
        Field[] destinationFields = destinationClass.getDeclaredFields();

        for (Field sourceField : sourceFields) {
            for (Field destinationField : destinationFields) {
                if (sourceField.getName().equals(destinationField.getName()) &&
                    sourceField.getType().equals(destinationField.getType())) {
                    sourceField.setAccessible(true);
                    destinationField.setAccessible(true);

                    Object valueToSet = sourceField.get(source);
                    if(!ignoreNullValues || valueToSet != null)
                        destinationField.set(destination, valueToSet);

                    sourceField.setAccessible(false);
                    destinationField.setAccessible(false);
                }
            }
        }
    }
}
