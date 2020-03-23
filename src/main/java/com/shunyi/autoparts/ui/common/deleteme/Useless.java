package com.shunyi.autoparts.ui.common.deleteme;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Useless {


    public static void main(String[] args) throws InterruptedException {
//        ReentrantLock lock = new ReentrantLock();
//        lock.lock();
//        System.out.println("锁定");
//        try {
//            long i = 0L;
//            while(i < 1000000L) {
//                i+=1;
//            }
//            System.out.println("执行完毕,i="+i);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            lock.unlock();
//            System.out.println("解锁");
//        }
//        new Useless().stepOver5();
//
//        Thread.holdsLock(new Useless());


        //============== 2019/10/30
//        BigDecimal number = new BigDecimal(10);
//        BigDecimal number2 = new BigDecimal(3);
//
//        BigDecimal value = number.divide(number2, 10, RoundingMode.CEILING);
//        System.out.println("value="+value);
        // 2019 10 31
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        try {
//            Date d= formatter.parse("2019-10-31T00:21:48.571+0000");
//            System.out.println(d);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        //==========2019-11-16
//        BigDecimal decimal = new BigDecimal(24.0);
//        System.out.println(decimal.doubleValue() == 24.0);

        //2019/11/12
        List<String> list1 = new ArrayList<>();
        list1.add("Green");
//        list1.add("Blue");
        List<String> list2 = new ArrayList<>();
        list2.add("XL");
//        list2.add("XXL");
        List<String> list3 = new ArrayList<>();
        list3.add("Small");
        list3.add("Middle");
        list3.add("Big");

//        Map<Long, List<String>> map = new HashMap<>();
//        map.put(1l, list1);
//        map.put(2l, list2);
//        map.put(3l, list3);
//        for(int i = 0;i < list1.size(); i++) {
//            for(int j = 0;j < list2.size();j++) {
//                for(int z = 0;z < list3.size();z++) {
//                    String str = list1.get(i)+","+list2.get(j)+","+list3.get(z);
//                    System.out.println(str);
//                }
//            }
//        }




        //2019/11/13
//        Useless useless = new Useless();
//        List<List<String>> cartItems = new ArrayList<>(map.values());
//        String rs = useless.multiRound(cartItems, "", 0);
//        System.out.println(rs);


        //2019/11/13
//        CB cb1 = new CB("Green", true);
//        CB cb2 = new CB("Blue", false);
//        CB cb3 = new CB("Red", true);
//        List<CB> cblist = new ArrayList<>();
//        cblist.add(cb1);
//        cblist.add(cb2);
//        cblist.add(cb3);
//
//        List<String> strlist = cblist.stream().filter(e -> e.isSelected()).map(e -> e.getName()).collect(Collectors.toList());
//        System.out.println(strlist.size());
//        for(String str : strlist) {
//            System.out.print(str+"  ");
//        }

        //2019/11/14
//        String str = "Green/XL/Small/$Green/XL/Middle/$Blue/XL/Small/$Blue/XL/Middle/$";
//
//        String[] newStr = str.split("\\$");
//        System.out.println(newStr.length);
//        for(String s : newStr) {
//            String[] news = s.split("/");
//            System.out.println(news.length+"  "+news[1]);
//        }

        //2019/11/26
//        String str = "Green:6-XL:3-Small:8";
//        String str = "Green:6/XL:3/Small:18/";
//        //去掉id
//        str = str.replaceAll(":\\d+/", "-");
//        str = str.substring(0, str.length()-1);
//        System.out.println("str="+str);


        ///2019/12/12
        List<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);
        a.add(5);

        List<Integer> b = new ArrayList<>();
        b.add(1);
        b.add(4);

//        a.addAll(b);
        //removable items
//        List<Integer> c = b.stream().filter(e -> !a.contains(e)).collect(Collectors.toList());
//        for(Integer ss : c) {
//            System.out.println(ss);
//        }

        List<Integer> c2 = a.stream().filter(e -> !b.contains(e)).collect(Collectors.toList());
        for(Integer ss : c2) {
            System.out.println(ss);
        }

    }

    public String multiRound(List<List<String>> dataList, String temp, int index) {
        if (index >= dataList.size()) {
            return "";
        }
        StringBuffer out = new StringBuffer();
        String tmp = "";
        List<String> data = dataList.get(index);

        for (int i = 0; i < data.size(); i++) {
            tmp = data.get(i) + "/";

            if (index < dataList.size()) {
                out.append(multiRound(dataList, temp + tmp, index + 1));
            }

            if (index == dataList.size() - 1) {
                out.append(temp).append(tmp).append("\n");
            }
        }

        return out.toString();
    }


   /*
    Geeen,XL,Small
    Geeen,XL,Middle
    Blue,XL,Small
    Blue,XL,Middle
   **/
    void stepOver1() {
        Thread t1 = new Thread(r1(null));

        Thread t2 = new Thread(r2(t1));

        Thread t3 = new Thread(r3(t2));
        t3.start();
        t1.start();
        t2.start();
    }

    void stepOver2() throws InterruptedException {
        Thread t1 = new Thread(r1(null));
        Thread t2 = new Thread(r2(null));
        Thread t3 = new Thread(r3(null));
        t3.start();
        t3.join();
        t2.start();
        t2.join();
        t1.start();
    }



    private static Object myLock1 = new Object();
    private static Object myLock2 = new Object();
    /**
     * 为什么要加这两个标识状态?
     * 如果没有状态标识，当t1已经运行完了t2才运行，t2在等待t1唤醒导致t2永远处于等待状态
     */
    private static Boolean t1Run = false;
    private static Boolean t2Run = false;
    void stepOver3() {
        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (myLock1){
                    System.out.println("产品经理规划新需求...");
                    t1Run = true;
                    myLock1.notify();
                }
            }
        });

        final Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (myLock1){
                    try {
                        if(!t1Run){
                            System.out.println("开发人员先休息会...");
                            myLock1.wait();
                        }
                        synchronized (myLock2){
                            System.out.println("开发人员开发新需求功能");
                            myLock2.notify();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (myLock2){
                    try {
                        if(!t2Run){
                            System.out.println("测试人员先休息会...");
                            myLock2.wait();
                        }
                        System.out.println("测试人员测试新功能");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread3.start();
        thread1.start();
        thread2.start();
    }



    void stepOver4() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("产品经理规划新需求");
            }
        });

        final Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("开发人员开发新需求功能");
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("测试人员测试新功能");
            }
        });
        executorService.submit(thread1);
        executorService.submit(thread2);
        executorService.submit(thread3);
        executorService.shutdown();
    }

    CyclicBarrier barrier1 = new CyclicBarrier(2);
    CyclicBarrier barrier2 = new CyclicBarrier(2);
    void stepOver5() {
        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("产品经理规划新需求");
                    //放开栅栏1
                    barrier1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        final Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //放开栅栏1
                    barrier1.await();
                    System.out.println("开发人员开发新需求功能");
                    //放开栅栏2
                    barrier2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        final Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //放开栅栏2
                    barrier2.await();
                    System.out.println("测试人员测试新功能");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        thread3.start();
        thread1.start();
        thread2.start();
    }




    Runnable r1(Thread waitThread) {
        return new Runnable() {
            @Override
            public void run() {
                if(waitThread != null) {
                    try {
                        waitThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Thread 1");
            }
        };
    }

    Runnable r2(Thread waitThread) {
        return new Runnable() {
            @Override
            public void run() {
                if(waitThread != null) {
                    try {
                        waitThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Thread 2");
            }
        };
    }

    Runnable r3(Thread waitThread) {
        return new Runnable() {
            @Override
            public void run() {
                if(waitThread != null) {
                    try {
                        waitThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Thread 3");
            }
        };
    }

}
