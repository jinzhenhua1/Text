package com.example.text.text1;

/**
 * <p> 不同线程对数据的操作都是在自己的本地内存中，因此数据不会同步，可以用Volatile，或者synchronized解决</p>
 * <p>System.out.println 也会造成影响。其内部使用了synchronized关键字</p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/7/10 8:58
 */
public class TextVolatile {
    int number = 0;
    public void add(){
        number++;
    }

    public void printLog(){
        for(int i = 0; i < 10; i++){
            number = number + 1;
            System.out.println(Thread.currentThread().getName() + " is printing " + number);
        }
    }

    boolean flag = false;
    public void run(){
        while (!flag){
            number++;
//            System.out.println(Thread.currentThread().getName() + " update number value: " + number);
        }
    }

    public static void main(String[] args) {
        TextVolatile myData = new TextVolatile();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
//                myData.printLog();
                myData.run();
//                System.out.println(Thread.currentThread().getName() + " update number value: " + myData.number);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                myData.printLog();
            }
        });

        t1.start();
//        t2.start();
        try{
            Thread.sleep(1000);
        }catch (Exception e){

        }

        myData.flag = true;
        System.out.println(Thread.currentThread().getName() + " stope " + myData.number);
        try{
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " stope " + myData.number);
        }catch (Exception e){

        }
    }

}
