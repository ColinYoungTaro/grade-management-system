package com.yxxt.gradems.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CopyUtil {

    /**
     * 单体复制
     */
    public static <T> T copy(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BeanUtils.copyProperties(source, obj);
        return obj;
    }

    /**
     * 列表复制
     */
    public static <T> List<T> copyList(List source, Class<T> clazz) {
        List<T> target = new ArrayList<>();
        if (!CollectionUtils.isEmpty(source)){
            for (Object c: source) {
                T obj = copy(c, clazz);
                target.add(obj);
            }
        }
        return target;
    }

    public static void copyIgnoreNull(Object source,Object target){
        if(source == null || target == null ){
            System.out.println("cannt be null");
            return;
        }
        Class sourceClass = source.getClass();
        Class targetClass = target.getClass();

        List<Field> srcFields = new LinkedList<>();
        while(sourceClass != null && sourceClass != Object.class){
            Field[] sourceFields = sourceClass.getDeclaredFields();
            srcFields.addAll(Arrays.asList(sourceFields));
            sourceClass = sourceClass.getSuperclass();
        }

        while(targetClass != null && targetClass != Object.class){
            for(Field sField : srcFields){
                String name = sField.getName();
                sField.setAccessible(true);
                try{
                    Field tField = targetClass.getDeclaredField(name);
                    tField.setAccessible(true);
                    Object value = sField.get(source);
                    if(value != null){
                        tField.set(target, value);
                    }
                    //srcFields.remove(sField);
                }
                catch(SecurityException e){
                    e.printStackTrace();
                }
                catch (NoSuchFieldException e) {}
                catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            targetClass = targetClass.getSuperclass();
        }
    }
}
