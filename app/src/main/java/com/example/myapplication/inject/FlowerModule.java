package com.example.myapplication.inject;

import com.example.myapplication.inject.annotation.LilyFlower;
import com.example.myapplication.inject.annotation.RoseFlower;

import dagger.Module;
import dagger.Provides;

/**
 * Created on 2019-05-07.
 */
@Module
public class FlowerModule {

  @Provides
  @RoseFlower
  Flower provideRose() {
    return new Rose();
  }

  @Provides
  @LilyFlower
  Flower provideLily() {
    return new Lily();
  }

  @Provides
  Rose provideRoseSelf() {
    return new Rose();
  }
}
