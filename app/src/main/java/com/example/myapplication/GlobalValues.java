package com.example.myapplication;

import android.content.Context;

/**
 * Created on 2019-08-15.
 */
public class GlobalValues {
  static Context mContext;

  private GlobalValues() {
  }

  public static GlobalValues getInstance(Context context) {
    mContext = context;
    return Holder.instance;
  }

  private static class Holder {
    private static final GlobalValues instance = new GlobalValues();
  }
}
