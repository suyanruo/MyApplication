package com.example.myapplication.inject;

import com.example.myapplication.inject.Flower;

import javax.inject.Inject;

/**
 * Created on 2019-05-07.
 */
public class Rose extends Flower {

  @Override
  public String whisper()  {
    return "热恋";
  }
}
