/**
 * @Auther: PC-8
 * @Date: 2021-01-20 15:50
 * @Description:
 */
package com.example.controller;

/**
 * @Author: lcy
 * @Date: 2021-01-20 15:50
 * @Description:
 */
public class ThreadTest implements Runnable {
    @Override
    public void run(){
        System.out.println("1111111111");
    }


    public static void main(String[] args) {
        ThreadTest s = new ThreadTest();
        Thread thread = new Thread(s);

        thread.start();
    }
}

