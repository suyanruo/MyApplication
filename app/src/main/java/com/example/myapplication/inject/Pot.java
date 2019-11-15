package com.example.myapplication.inject;

import android.content.Context;

import com.example.myapplication.inject.annotation.LilyFlower;

import javax.inject.Inject;

/**
 * Created on 2019-05-07.
 */
public class Pot {

  private Context context;

  private Flower flower;

  @Inject
  public Pot(@LilyFlower Flower flower) {
    this.flower = flower;
  }

  public String show() {
    return flower.whisper();
  }

  public void setContext(Context context) {
    this.context = context;
  }

  public Context getContext() {
    return context;
  }
}
