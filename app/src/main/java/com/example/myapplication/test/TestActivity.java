package com.example.myapplication.test;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.GlobalValues;
import com.example.myapplication.R;

public class TestActivity extends AppCompatActivity {
  private static final String TAG = "TestActivity";
  private GlobalValues globalValues;
  private Handler handler = new Handler();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);

    globalValues = GlobalValues.getInstance(this);
    Log.e(TAG, "onCreate");
    Log.e(TAG, "globalValues:" + globalValues.toString());
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {

      }
    }, 60000);
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.e(TAG, "onPause");
    finish();
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.e(TAG, "onDestroy");
  }
}
