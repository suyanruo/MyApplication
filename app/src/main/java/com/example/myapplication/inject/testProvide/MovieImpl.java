package com.example.myapplication.inject.testProvide;

import javax.inject.Inject;

/**
 * Created on 2019-05-23.
 */
public class MovieImpl implements IMovie {
  private String name = "implements Movie";
  @Inject
  public MovieImpl() {

  }

  @Override
  public String showMovie() {
    return name;
  }
}
