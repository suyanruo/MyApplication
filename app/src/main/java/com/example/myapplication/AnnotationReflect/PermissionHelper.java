package com.example.myapplication.AnnotationReflect;

import android.app.Activity;

import com.example.myapplication.AnnotationReflect.annotation.PermissionGranted;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created on 2019-05-08.
 */
public class PermissionHelper {

  public static void requestPermission(Activity activity, String permission) {
    Class c = activity.getClass();
    Method[] methods = c.getDeclaredMethods();
    for (Method method : methods) {
      if (method.isAnnotationPresent(PermissionGranted.class)) {
        try {
          method.invoke(activity);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
