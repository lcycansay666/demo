/**
 * @Auther: PC-8
 * @Date: 2021-01-20 15:51
 * @Description:
 */
package com.example.controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;

/**
 * @Author: lcy
 * @Date: 2021-01-20 15:51
 * @Description:
 */
public class test2 implements Callable<String> {


    @Override
    public String call() throws Exception {
        return "aaaa";
    }


    public static void main(String[] args) {
        test2 s = new test2();
        try {
            System.out.println(s.call());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}