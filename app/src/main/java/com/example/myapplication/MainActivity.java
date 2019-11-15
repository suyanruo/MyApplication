package com.example.myapplication;

import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.AnnotationReflect.TestClass;
import com.example.myapplication.AnnotationReflect.annotation.BindAddress;
import com.example.myapplication.AnnotationReflect.annotation.BindGet;
import com.example.myapplication.AnnotationReflect.annotation.BindPort;
import com.example.myapplication.Vibrate.DebugService;
import com.example.myapplication.AnnotationReflect.annotation.PermissionGranted;
import com.example.myapplication.inject.DaggerMainActivityComponent;
import com.example.myapplication.inject.testProvide.AMovie;
import com.example.myapplication.inject.testProvide.IMovie;
import com.example.myapplication.test.TestActivity;
import com.example.myapplication.webview.JavaScriptActivity;
import com.example.myapplication.zendesk.ZendeskActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {
  public static String FILE_PATH = Environment.getExternalStorageDirectory() + "/DCIM/Camera/IMG_20170901_171324.jpg";

//  @Inject
//  Pot pot;
//
//  @Inject
//  @LilyFlower
//  Flower flower;

  @Inject
  AMovie aMovie;

  @Inject
  IMovie iMovie;

  @Inject
  String movieName;

  public static String patternStr = "^[\\w]{6,32}$";
  public static String patternStr2 = "^[\\W]{1,30}$";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // 这个类是重新编译后Dagger2自动生成的，所以写这行代码之前要先编译一次
    // Build --> Rebuild Project
    DaggerMainActivityComponent.create().inject(this);

//    String show = pot.show();
//    Toast.makeText(MainActivity.this, show + flower.whisper(), Toast.LENGTH_SHORT).show();
    Toast.makeText(MainActivity.this, iMovie.showMovie() + "   " + aMovie.showMovie() + "  " + movieName, Toast.LENGTH_SHORT).show();

//    PermissionHelper.requestPermission(this, "abc");
//    printIP();

    findViewById(R.id.btn_js).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, JavaScriptActivity.class));
      }
    });
    findViewById(R.id.btn_debug_service).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startDebugService();
      }
    });
    findViewById(R.id.btn_zendesk).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, ZendeskActivity.class));
      }
    });

    findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, TestActivity.class));
      }
    });

    String test = "Русскийязык";
    Pattern pattern = Pattern.compile(patternStr);
    Matcher matcher = pattern.matcher(test);
    boolean b= matcher.matches();
//当条件满足时，将返回true，否则返回false
    Log.e(TAG, "onCreate: " + b + "\n w:" + test.matches(patternStr) + "\n W:" + test.matches(patternStr2));
  }

//  @Inject
//  public void setPotContext() {
//    pot.setContext(this);
//  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Log.e(TAG, "onSaveInstanceState");
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    Log.e(TAG, "onRestoreInstanceState");
  }

  private static final String TAG = "MainActivity";
  @PermissionGranted("abc")
  public void permissionGranted() {

    Toast.makeText(MainActivity.this, "Granted", Toast.LENGTH_SHORT).show();
  }

  public void printIP() {
    try {
      Class c = Class.forName("com.example.myapplication.AnnotationReflect.TestClass");
      TestClass tc = (TestClass) c.newInstance();
      Field[] fields = c.getDeclaredFields();
      for (Field field : fields) {
        if (field.isAnnotationPresent(BindAddress.class)) {
          BindAddress address = field.getAnnotation(BindAddress.class);
          field.setAccessible(true);
          field.set(tc, address.value());
        }
        if (field.isAnnotationPresent(BindPort.class)) {
          BindPort port = field.getAnnotation(BindPort.class);
          field.setAccessible(true);
          field.set(tc, port.value());
        }
      }
      tc.printInfo();

      Method[] methods = c.getDeclaredMethods();
      for (Method method : methods) {
        if (method.isAnnotationPresent(BindGet.class)) {
          BindGet get = method.getAnnotation(BindGet.class);
          method.invoke(tc, get.name(), get.age());
        }
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

  }


  public void startDebugService() {
    Intent service = new Intent(this, DebugService.class);
    startService(service);
  }


}
