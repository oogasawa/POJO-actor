package net.laddercode.pojoactor.example.chap01.ex02;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.zakgof.actr.Actr;
import com.zakgof.actr.IActorRef;
import com.zakgof.actr.IActorScheduler;
import com.zakgof.actr.IActorSystem;
import com.zakgof.actr.impl.SingleThreadScheduler;
import com.zakgof.actr.impl.ThreadPerActorScheduler;

/** Creating and understanding ActorSystem */
public class SummingApp2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        SummingApp2 obj = new SummingApp2();
        obj.runExample();

    }

    public void runExample() throws InterruptedException, ExecutionException {
        // IActorScheduler scheduler = new SingleThreadScheduler();
         IActorScheduler scheduler = new ThreadPerActorScheduler();
        IActorSystem actorSystem = Actr.newSystem("summing", scheduler);
        //IActorSystem actorSystem = Actr.newSystem("summing");
        final IActorRef<Calculator> calcActor = actorSystem.actorOf(Calculator::new, "calculator");
        final IActorRef<Starter> starter
            = actorSystem.actorOf(()->new Starter(calcActor), "starter");

        starter.tell(s -> {
            try{
                s.start();
            } catch(Exception e) {
                System.out.println("unexpected error");
            }
        });

        actorSystem.shutdown().join();
    }

}
