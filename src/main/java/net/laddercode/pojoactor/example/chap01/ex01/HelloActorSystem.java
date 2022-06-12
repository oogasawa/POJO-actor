package net.laddercode.pojoactor.example.chap01.ex01;

import com.zakgof.actr.Actr;
import com.zakgof.actr.IActorSystem;

/** Creating and understanding ActorSystem */
public class HelloActorSystem {

    public static void main(String[] args) {
        HelloActorSystem obj = new HelloActorSystem();
        obj.runExample();

    }

    public void runExample() {
        IActorSystem actorSystem = Actr.newSystem("default");
        System.out.println(actorSystem);
        actorSystem.shutdown();
    }

}
