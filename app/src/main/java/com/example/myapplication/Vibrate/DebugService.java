package com.example.myapplication.Vibrate;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Vibrator;

import java.util.List;

public class DebugService extends Service implements SensorEventListener {
  private static int ACCELERATION = 80;
  private static int ACCELERATION_ONE = 15;
  private static int TIME_INTERVAL = 1000;
  private static int ROCK_MIN_NUM = 4;
  private static int ROCK_MAX_INTERVAL = 500;
  private SensorManager sensorManager;
  private Vibrator mVibrator = null;
  private long mPreLotteryTime = 0;
  private float mLastX = 0;
  private float mLastY = 0;
  private float mLastZ = 0;
  private int mRockNum = 0;
  private long mPreRockTime = 0;
  private boolean mValidRock = false;
  private boolean mRockPage = true;
  private String mPackageName;
  private ActivityManager mActivityManager = null;
  private boolean stopSelf;

  public DebugService() {
  }

  @Override
  public void onCreate() {
    super.onCreate();

    mPackageName = DebugService.this.getPackageName();
    mActivityManager = (ActivityManager) DebugService.this.getSystemService(DebugService.this.ACTIVITY_SERVICE);

    initSensor();
  }

  private void initSensor() {
    if (sensorManager != null) {
      return;
    }
    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
  }

  private void unregisterSensorListener() {
    if (sensorManager == null) {
      return;
    }
    sensorManager.unregisterListener(this);
  }

  @Override
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public void onStart(Intent intent, int startId) {
    super.onStart(intent, startId);
    stopSelf = false;
    if (intent != null) {
      stopSelf = intent.getBooleanExtra("stop", false);
    }

    if (stopSelf) {
      stopSelf();
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    unregisterSensorListener();
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    int sensorType = event.sensor.getType();
    if (sensorType != Sensor.TYPE_ACCELEROMETER || event.values == null || event.values.length < 3) {
      return;
    }

    float x = event.values[0];
    float y = event.values[1];
    float z = event.values[2];
    float deltaX = x - mLastX;
    float deltaY = y - mLastY;
    float deltaZ = z - mLastZ;
    mLastX = x;
    mLastY = y;
    mLastZ = z;
    if (!mRockPage) {
      return;
    }

    if (!mValidRock) {
      mValidRock = true;
      return;
    }

    double speed = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;

    if (speed >= ACCELERATION / 3) {

    }

    if (speed < ACCELERATION) {
      return;
    }

    if (!isProcessOnForeground() || !isScreenOn()) {
      return;
    }


    long current = System.currentTimeMillis();

    if (current - mPreRockTime <= ROCK_MAX_INTERVAL) {
      mRockNum++;
    } else {
      mRockNum = 1;
    }

    if (Math.abs(deltaY) >= ACCELERATION_ONE || Math.abs(deltaX) >= ACCELERATION_ONE
        || Math.abs(deltaZ) >= ACCELERATION_ONE) {
      mRockNum = ROCK_MIN_NUM;
    }

    mPreRockTime = current;

    if (mRockNum < ROCK_MIN_NUM) {
      return;
    }

    //生效的摇动在间隔时间内只执行一次
    if (current - mPreLotteryTime < TIME_INTERVAL) {
      return;
    }

    mPreLotteryTime = current;

    mVibrator.vibrate(100);

    Intent intent = new Intent(getApplicationContext(), DebugActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    startActivity(intent);
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {

  }

  /*
   * 判断当前进程是否处于前台
   */
  private boolean isProcessOnForeground() {
    List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager.getRunningAppProcesses();
    if (null == appProcessList) {
      return false;
    }
    for (int i = 0; i < appProcessList.size(); i++) {
      ActivityManager.RunningAppProcessInfo appProcessInfo = appProcessList.get(i);
      String processName = appProcessInfo.processName;
      if (mPackageName.contains(processName)
          && appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
        return true;
      }
    }
    return false;
  }

  /*
   * 判断是否锁屏
   */
  private boolean isScreenOn() {
    PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
    return pm != null && pm.isScreenOn();
  }
}
