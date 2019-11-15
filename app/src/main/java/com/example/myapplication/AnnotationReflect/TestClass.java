package com.example.myapplication.AnnotationReflect;

import android.util.Log;

import com.example.myapplication.AnnotationReflect.annotation.BindAddress;
import com.example.myapplication.AnnotationReflect.annotation.BindGet;
import com.example.myapplication.AnnotationReflect.annotation.BindPort;

/**
 * Created on 2019-05-08.
 */
public class TestClass {
  @BindAddress
  private String address;
  @BindPort("40")
  private String port;
  private int number;

  public void printInfo() {
    Log.d("TestClass", "info is " + address + ":" + port);
  }
  private void myMethod(int number,String sex) {

  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  @BindGet(name="Jack", age=25)
  public void getHttp(String param1, int param2){
    String url="http://www.baidu.com/?username=" + param1 + "&age=" + param2;
    Log.d("TestClass", "get------->"+url);
  }
}
