package com.example.myapplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2019-06-03.
 */
public class Test {
  public static String patternStr = "^[\\w]{6,32}$";

  public static void main(String[] args) {
    String test = "无言如宣言风铃如沧海";
    Pattern pattern = Pattern.compile(patternStr);
    Matcher matcher = pattern.matcher(test);
    boolean b= matcher.matches();
//当条件满足时，将返回true，否则返回false
    System.out.println("pattern: " + b + "\n string:" + test.matches(patternStr));
  }
}
