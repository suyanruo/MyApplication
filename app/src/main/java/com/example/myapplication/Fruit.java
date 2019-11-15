package com.example.myapplication;

/**
 * Created on 2019-05-08.
 */
public enum Fruit {
  APPLE("apple", 10),
  ORANGE("orange", 8),
  BANANA("banana", 7);

  public String name;
  public int price;

  Fruit(String name, int price) {
    this.name = name;
    this.price = price;
  }
}
