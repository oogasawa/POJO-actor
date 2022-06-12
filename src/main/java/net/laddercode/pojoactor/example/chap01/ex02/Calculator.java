package net.laddercode.pojoactor.example.chap01.ex02;

import java.util.ArrayList;

public class Calculator {

    int sum = 0;
    ArrayList<Integer> result = new ArrayList<>();

    public Calculator() {
        sum = 0;
    }


    public int add(int num)  {
        try {
                Thread.sleep(1000);
        } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        sum += num;
        result.add(num);
        return sum;
    }


    public void report() {
        result.stream().forEach(e->System.out.println(e));
        //System.out.println(sum);
    }
}
