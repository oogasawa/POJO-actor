package net.laddercode.pojoactor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import net.laddercode.pojoactor.pojo.Adder;

public class App {

    public static void main(String[] args) {

        ActorSystem system = new ActorSystem("system1");


        Adder adder = new Adder();
        ActorRef<Adder> a1 = system.actorOf("actor1", adder);
        CompletableFuture<Integer> result = (CompletableFuture<Integer>)a1.ask((a)->a.add(10));

        try {
            System.out.println(result.get());
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        system.terminate();

    }

}
