package com.example.myapplication.inject;

import com.example.myapplication.MainActivity;
import com.example.myapplication.inject.testProvide.MovieModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created on 2019-05-07.
 */
@Component(modules = {
    FlowerModule.class,
    MovieModule.class
})
public interface MainActivityComponent {
  void inject(MainActivity activity);
}
