package com.example.demo;

import java.lang.reflect.Field;


public class TestUtils {
    public static void injectObjects(Object target , String fieldname , Object toInject) throws NoSuchFieldException, IllegalAccessException {

        boolean wasprivate = false;
        Field f = target.getClass().getDeclaredField(fieldname);
        if(!f.isAccessible()){
            f.setAccessible(true);
            wasprivate = true;
        }
        f.set(target , toInject);
        if(wasprivate){
            f.setAccessible(false);
        }
    }
}
