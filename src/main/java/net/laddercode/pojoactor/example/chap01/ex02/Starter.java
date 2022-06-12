package net.laddercode.pojoactor.example.chap01.ex02;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.zakgof.actr.IActorRef;

public class Starter {

    private IActorRef<Calculator> calcActor;

    public Starter(IActorRef<Calculator> calcActor) {
		this.calcActor = calcActor;
	}


    public void start() throws InterruptedException, ExecutionException, TimeoutException {

        CompletableFuture<Integer> f = this.calcActor.ask(c->{ return c.add(10);});
        new Thread(()->{
                 f.completeOnTimeout(-1, 5000, TimeUnit.MILLISECONDS);
         }).start();
        System.out.println(f.get());

        this.calcActor.tell(c->c.add(3));
        this.calcActor.tell(c->c.report());


        //System.out.println(result.get(10, TimeUnit.SECONDS));
    }

}
