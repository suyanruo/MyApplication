package com.example.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class BaseActivity extends AppCompatActivity {
  private static final String TAG = "BaseActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_base);
//    Toolbar toolbar = findViewById(R.id.toolbar);
//    setSupportActionBar(toolbar);
//
//    FloatingActionButton fab = findViewById(R.id.fab);
//    fab.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show();
//      }
//    });
  }

  @Override
  public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);

  }

  /**
   * 在Manifest中设置activity的label可以通过onTitleChanged()反馈得到title，再借助BaseActivity中的ActionBar设置标题。 
   */
  @Override
  protected void onTitleChanged(CharSequence title, int color) {
    super.onTitleChanged(title, color);
    Log.e(TAG, "onTitleChanged: " + title);
  }
}
