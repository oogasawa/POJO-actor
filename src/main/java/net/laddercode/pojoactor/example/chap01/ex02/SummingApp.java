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
public class SummingApp {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        SummingApp obj = new SummingApp();
        obj.runExample();

    }

    public void runExample() throws InterruptedException, ExecutionException {
        //IActorScheduler scheduler = new ThreadPerActorScheduler();
        //IActorScheduler scheduler = new SingleThreadScheduler();
        //IActorSystem actorSystem = Actr.newSystem("summing", scheduler);
        IActorSystem actorSystem = Actr.newSystem("summing");
        final IActorRef<Calculator> calcActor = actorSystem.actorOf(Calculator::new, "calculator");

        calcActor.tell(s -> {
            s.add(3);
        });
        // calcActor.tell(s -> {
        //     s.report();
        // });
        calcActor.tell(s -> {
            s.add(4);
        });
        // calcActor.tell(s -> {
        //     s.report();
        // });
        CompletableFuture<Integer> f = calcActor.ask(s -> {
            return s.add(2);
        });
        System.out.println(f.get());
        calcActor.tell(s -> {s.report();});

        actorSystem.shutdown();
    }

}
