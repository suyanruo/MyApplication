package com.example.myapplication.inject.testProvide;

import dagger.Module;
import dagger.Provides;

/**
 * Created on 2019-05-23.
 */
@Module
public class MovieModule {

//  @Provides
//  public MovieImpl provideMovie() {
//    return new MovieImpl();
//  }

  @Provides
  public IMovie provideIMovie(MovieImpl movie) {
    return movie;
  }

  @Provides
  public AMovie provideAMovie(MovieExt movieExt) {
    return movieExt;
  }

  @Provides
  public String provideMovie() {
    return "movieName";
  }

//  @Provides
//  public IMovie provideIMovie(String params) {
//    return new MovieImpl();
//  }
//
//  @Provides
//  public String provideParam() {
//    return "疯狂动物城";
//  }

}
