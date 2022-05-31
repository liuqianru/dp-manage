package com.platform.modules.qkjvip.controller;

public class text {
    static final Object pc = new Object();
    static  boolean isPrettyGril = false; // 有没有条件
    static boolean ismoney = false;// 有没有钱
    public static void main(String[] args) {
        new Thread(()->{
            synchronized (pc){
                System.out.println("有没有成功"+isPrettyGril);
                if (!isPrettyGril) {
                    System.out.println("没成功，等5秒;别人也不干活。");
                    try {
                        pc.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("5秒后有没有？"+ isPrettyGril);
                    if (isPrettyGril) {
                        System.out.println("老板来了，开始工作");
                    } else {
                        System.out.println("下班");
                    }
                }
            }
        },"sun").start();
        new Thread(()->{
            synchronized (pc){
                System.out.println("有没有钱？"+ismoney);
                if (!ismoney) {
                    System.out.println("没钱，等;");
                    try {
                        pc.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("有没有钱？"+ ismoney);
                    if (ismoney) {
                        System.out.println("钱来了，开始工作");
                    } else {
                        System.out.println("没钱，下班");
                    }
                }
            }
        },"sun2").start();
        for (int i=0;i<10;i++){
            new Thread(()->{
                synchronized (pc){
                    System.out.println("老实人：10个人搬砖了");
                }
            },"other").start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            synchronized (pc){
                isPrettyGril = true;
                System.out.println("给我工作！！！！");
                ismoney = true;
                System.out.println("钱来了，给我工作");
                pc.notifyAll();

            }
        },"boss").start();
    }
}
