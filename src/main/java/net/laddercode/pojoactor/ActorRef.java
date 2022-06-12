package net.laddercode.pojoactor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Interface for addressing actors.
 *
 * @param <T> actor POJO class
 */
public class ActorRef<T> implements AutoCloseable {

    private final String actorName;

    private volatile T object;

    private ActorSystem actorSystem = null;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    ConcurrentSkipListSet<String> NamesOfChildren = new ConcurrentSkipListSet<>();
    String parentName;
    //private final BiConsumer<T, Exception> exceptionHandler;
    //private final Consumer<T> destructor;private volatile Object box;
    //private volatile IRegistration reg;

    ActorRef(String actorName, T object) {
        this.actorName = actorName;
        this.object = object;
    }

    ActorRef(String actorName, T object, ActorSystem actorSystem) {
        this.actorName = actorName;
        this.object = object;
        this.actorSystem = actorSystem;
    }


    public  ActorRef<T> createChild(String actorName, T object) {
        ActorRef<T> child = this.actorSystem.actorOf(actorName, object);
        child.setParentName(this.actorName);
        this.NamesOfChildren.add(actorName);

        return child;
    }


    public ConcurrentSkipListSet<String> getNamesOfChildren() {
        return this.NamesOfChildren;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }


    public ActorSystem system() {
        return this.actorSystem;
    }


    /**
     * @return actor's @link {ActorSystem}
     */
    //IActorSystem system();

    /**
     * Sends a message to the actor defined by this reference.
     *
     * The specified action is executed on the actor's object asynchronously in actor's thread context. This method does not wait for completion of the action, it returns immediately.
     *
     * @param action action to be executed on actor's object.
     */
    void tell(Consumer<T> action) {
        T target = this.object;
        CompletableFuture.runAsync(()->action.accept(target), executor);
    }
    // /**
    //  * Schedules an action to be executed once after a specified time.
    //  *
    //  * The specified action is executed on the actor's object asynchronously in actor's thread context.
    //  *
    //  * @param action action to be executed on actor's object.
    //  * @param ms delay in milliseconds
    //  */
    // void later(Consumer<T> action, long ms);


    /**
     * Sends a message to actor and returns a CompletableFuture to be completed with the response value.
     *
     * Performs the specified call on the actor's object asynchronously. The call is executed in this actor's thread context, the future is then completed with the result value in the caller's actor thread context. If the method is
     * called not from actor's context, exception is thrown.
     *
     * This method returns a CompletableFuture, which is completed with a result once the actor's call completes. If an exception occurs during actor's call, the exception is then passed to the CompletableFuture and the actor's exception handler is not triggered. Both successful and failed completions occur in the caller's actor thread context.
     *
     * @param <R> actor call response class
     * @param action action to be executed on actor's object, return value will be the response
     * @return CompletableFuture to be completed with the actor's call result
     */
    public <R> CompletableFuture<R> ask(Function<T, R> action) {
        T target = this.object;
        CompletableFuture<R> task
            = CompletableFuture.supplyAsync(()->{ return action.apply(target);}, executor);

        return task;
    }

    // /**
    //  * Sends a message to actor and returns a CompletableFuture to be completed with the response value.
    //  *
    //  * Performs the specified call on the actor's object asynchronously. The call is executed in this actor's thread context, the future is then completed with the result value in the caller's actor thread context. If the method is
    //  * called not from actor's context, exception is thrown.
    //  *
    //  * This method returns a CompletableFuture, which is completed with a result once the actor's call completes. If an exception occurs during actor's call, the exception is then passed to the CompletableFuture and the actor's exception handler is not triggered. Both successful and failed completions occur in the caller's actor thread context.
    //  *
    //  * @param <R> actor call response class
    //  * @param action action to be executed on actor's object; the BiConsumer accepts the actor's object and the callback to receive the actor's call result.
    //  * @return CompletableFuture to be completed with the actor's call result
    //  */
    // <R> CompletableFuture<R> ask(BiConsumer<T, Consumer<R>> action);

    /**
     * Destroy the actor.
     *
     *
     * Messages pending for this actor will be processed before terminating (TODO ??)
     */
    @Override
    public void close() {
        if (this.system() != null && system().hasActor(this.actorName)) {
            system().removeActor(this.actorName);
        }
        this.object = null;
        executor.shutdown();
    }

}
