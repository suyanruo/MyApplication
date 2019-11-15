package com.example.myapplication.inject.testProvide;

import javax.inject.Inject;

/**
 * Created on 2019-05-23.
 */
public class MovieExt extends AMovie {
  private String name = "extends Movie";
  @Inject
  public MovieExt() {

  }

  @Override
  public String showMovie() {
    return name;
  }
}
