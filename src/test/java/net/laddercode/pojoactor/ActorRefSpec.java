package net.laddercode.pojoactor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import net.laddercode.pojobdd.BddUtil;

public class ActorRefSpec {

    public static boolean exec() {

        try (PrintStream out = BddUtil.newPrintStream("ActorRef_spec.md")) {
            // Checks if all the tests are succeeded.
            List<Boolean> results = new ArrayList<Boolean>();
            results.add(tellSpec(out));

            out.flush();
            return BddUtil.allTrue(results);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }


    public static boolean tellSpec(PrintStream out) {

        // Description
        String description = """

            ## Send a message to an actor

            - Given an ActorRef object
            - When you send messages to the object
            - Then the messages are executed in the given order of the messages.

            Results:

            """;

        out.println(description);


        // Reality
        ActorRef<ArrayList<Integer>> arrayActor = new ActorRef<>("arrayActor", new ArrayList<Integer>());

        Stream.iterate(0, n->{return n<1000;}, n->{return ++n;})
            .forEach(n->arrayActor.tell((a)->a.add(n)));

        // arrayActor.tell((a)->a.add(1));
        // arrayActor.tell((a)->a.add(2));
        // arrayActor.tell((a)->a.add(3));
        // arrayActor.tell((a)->a.add(4));
        // arrayActor.tell((a)->a.add(5));


        CompletableFuture<String> f = (CompletableFuture<String>)arrayActor.ask((a)->a.toString());

        String answer = "";
        try {
            answer = f.get(3, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        arrayActor.close();

        // Expectations
        ArrayList<Integer> expList = new ArrayList<>();
        for (int i=0; i<1000; i++) {
            expList.add(i);
        }
        String expectation = expList.toString();

        // String[] expectations = {
        //     "[1, 2, 3, 4, 5]"
        // };
        // String expectation = String.join("\n",expectations);

        // Check the answer.
        boolean result = BddUtil.assertTrue(out, expectation, answer);
        assert result;
        return result;

        //return true;
    }


}
