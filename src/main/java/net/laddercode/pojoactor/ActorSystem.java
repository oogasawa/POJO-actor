package net.laddercode.pojoactor;

import java.util.concurrent.ConcurrentHashMap;

import net.laddercode.pojoactor.pojo.Root;

public class ActorSystem {

    private final String systemName;

    /**  A name to an ActorRef correspondance. */
    ConcurrentHashMap<String, ActorRef<?>> actors = new ConcurrentHashMap<>();

    public ActorSystem(String systemName) {
        this.systemName = systemName;

        // create the ROOT actor.
        this.actorOf("ROOT", new Root());
    }


    public <T> ActorRef<T> actorOf(String actorName, T object) {
        ActorRef<T> actor = new ActorRef<T>(actorName, object, this);
        actors.put(actorName, actor);
        return actor;
    }

    public <T> ActorRef<T> getActor(String actorName) {
        return (ActorRef<T>)actors.get(actorName);
    }


    public boolean hasActor(String actorName) {
        return this.actors.containsKey(actorName);
    }

    public void removeActor(String actorName) {
        actors.remove(actorName);
    }

    public ActorRef<Root> root() {
        return this.getActor("ROOT");
    }

    public void terminate() {
        actors.keySet().stream()
            .forEach((name)->actors.get(name).close());
    }


    public String toString() {
        return String.format("{actorSystem: \"%s\"}", systemName);
    }

}
