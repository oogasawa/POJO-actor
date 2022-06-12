package net.laddercode.pojoactor.example;

import net.laddercode.pojoactor.example.chap01.ex01.HelloActorSystem;

public class App {

    public static void main(String[] args) {
        System.out.println("--- ex01 HelloActorSystem ---");
        HelloActorSystem obj01a = new HelloActorSystem();
        obj01a.runExample();
    }
}
