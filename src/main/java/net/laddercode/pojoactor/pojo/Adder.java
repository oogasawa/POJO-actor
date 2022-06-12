package net.laddercode.pojoactor.pojo;


public class Adder {

    int state = 0;

    public int add(int n) {
        this.state += n;
        return this.state;
    }


    public int getState() {
        return this.state;
    }

}
