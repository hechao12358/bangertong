package com.wode.bangertong.controller;

import java.lang.reflect.Method;

public class BeanExample {

    private String propertyName;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Object getPropertyValueViaName(Object bean, String propertyName) throws Exception {
        String getterMethodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        Method getterMethod = bean.getClass().getMethod(getterMethodName);
        return getterMethod.invoke(bean);
    }

    public void setPropertyValueViaName(Object bean, String propertyName, Object value) throws Exception {
        String setterMethodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        Class<?>[] types = new Class[]{value.getClass()};
        Method setterMethod = bean.getClass().getMethod(setterMethodName, types);
        setterMethod.invoke(bean, value);
    }

//    public static void main(String[] args) throws Exception {
//        BeanExample beanExample = new BeanExample();
//        beanExample.setPropertyName("ExampleProperty");
//
//        System.out.println("Before: " + beanExample.getPropertyName()); // Output: Before: ExampleProperty
//
//        // Update property value via reflection
//        beanExample.setPropertyValueViaName(beanExample, "propertyName", "NewExampleProperty");
//
//        System.out.println("After: " + beanExample.getPropertyName()); // Output: After: NewExampleProperty
//    }
}