package com.example.myapplication;

import java.util.Stack;

/**
 * Created on 2019-05-24.
 */
public class TestCode {

  public static void main(String[] args) {
    Stack<Integer> test = new Stack<Integer>();
    test.push(1);
    test.push(2);
    test.push(3);
    test.push(4);
    test.push(5);
    reverse(test);
    while (!test.isEmpty()) {
      System.out.println(test.pop());
    }
  }

  public static int removeLast(Stack<Integer> stack) {
    int result = stack.pop();
    if (stack.isEmpty()) {
      return result;
    }
    int next = removeLast(stack);
    stack.push(result);
    return next;
  }

  public static void reverse(Stack<Integer> stack) {
    if (stack.isEmpty()) {
      return;
    }
    int last = removeLast(stack);
    reverse(stack);
    stack.push(last);
  }
}
