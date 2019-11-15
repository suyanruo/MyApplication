package com.example.myapplication.inject.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created on 2019-05-07.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface LilyFlower {}
