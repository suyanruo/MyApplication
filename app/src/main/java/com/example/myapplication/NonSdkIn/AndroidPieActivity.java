package com.example.myapplication.NonSdkIn;

import android.content.ContextWrapper;
import android.gesture.Gesture;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AndroidPieActivity extends AppCompatActivity {
  private static final String TAG = "AndroidPieActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_android_pie);

    findViewById(R.id.btn_dark_gray_list).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        darkGrayList();
      }
    });

    findViewById(R.id.btn_black_list).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        blackList();
      }
    });
  }

  private void darkGrayList() {
    // https://android.googlesource.com/platform/prebuilts/runtime/+/master/appcompat/hiddenapi-dark-greylist.txt
    // Landroid/content/ContextWrapper;->getOpPackageName()Ljava/lang/String;
    Method method;
    try {
      method = new  ContextWrapper(AndroidPieActivity.this).getClass().getDeclaredMethod("getOpPackageName");
      method.setAccessible(true);
      Log.e(TAG, method.invoke(getApplicationContext()).toString());
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
      Log.e(TAG, "NoSuchMethodException ");
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      Log.e(TAG, "IllegalAccessException ");
    } catch (InvocationTargetException e) {
      e.printStackTrace();
      Log.e(TAG, "InvocationTargetException ");
    }
  }

  private void blackList() {
    Gesture gesture = new Gesture();
    try {
      Method method = gesture.getClass().getDeclaredMethod("setID", Long.class);
      method.setAccessible(true);
      method.invoke(gesture, 1L);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}
