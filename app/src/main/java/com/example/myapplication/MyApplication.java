package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;

import com.zendesk.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import zendesk.core.AnonymousIdentity;
import zendesk.core.Identity;
import zendesk.core.Zendesk;
import zendesk.support.Support;

/**
 * Created on 2019-05-24.
 */
public class MyApplication extends Application {
  private static final String TAG = "MyApplication";

  static MyApplication instance;

//  private static final ExecutorService LISTENER_EXECUTOR = Executors.newSingleThreadExecutor();
//  private static File logDir = new File(Environment.getExternalStorageDirectory(), "__greylist");
//  private final static StrictMode.OnVmViolationListener GREYLISTENER =
//      new StrictMode.OnVmViolationListener() {
//        @Override
//        public void onVmViolation(Violation violation) {
//          logDir.mkdirs();
//
//          String name = SystemClock.uptimeMillis() + ".txt";
//          File trace = new File(logDir, name);
//
//          try {
//            FileOutputStream fos = new FileOutputStream(trace);
//            OutputStreamWriter osw = new OutputStreamWriter(fos);
//            PrintWriter out = new PrintWriter(osw);
//
//            violation.printStackTrace(out);
//            out.flush();
//            fos.getFD().sync();
//            out.close();
//          } catch (IOException e) {
//            Log.e(TAG, "Exception writing trace", e);
//          }
//        }
//      };

  public static MyApplication getApplication() {
    return instance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    init();
  }

  public void init() {
    Logger.setLoggable(true);

//    Zendesk.INSTANCE.init(this, "https://dimghost2.zendesk.com",
//        "d4e7555f50c8bd773aa5ac9b546f55c282dd3d51af2ac0c2",
//        "mobile_sdk_client_b41dd7ead2270f6bdeb0");
//    Support.INSTANCE.init(Zendesk.INSTANCE);

    Zendesk.INSTANCE.init(this, "https://getgranafacil.zendesk.com",
        "200ca3ed2d29732761df644a6ea824812af50e4291beadb0",
        "mobile_sdk_client_689e2880f90226d631b0");
    Support.INSTANCE.init(Zendesk.INSTANCE);

    setIdentity("zj", "dimghost1@gmail.com");

    checkNonSdk();
  }

  public void setIdentity(String name, String email) {
    Identity identity = new AnonymousIdentity.Builder()
      .withNameIdentifier(name)
      .withEmailIdentifier(email)
      .build();
//    Identity identity = new AnonymousIdentity();
    Zendesk.INSTANCE.setIdentity(identity);
  }

  private void checkNonSdk() {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//      if (logDir.listFiles() != null) {
//        for (File file : logDir.listFiles()) {
//          if (!file.isDirectory()) {
//            file.delete();
//          }
//        }
//      }
//      StrictMode.setVmPolicy(
//          new StrictMode.VmPolicy
//              .Builder()
//              .detectNonSdkApiUsage()
//              .penaltyListener(LISTENER_EXECUTOR, GREYLISTENER)
//              .build());
//    }
  }
}
