package net.laddercode.pojoactor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.laddercode.pojobdd.BddUtil;

public class ActorSystemSpec {


    public static boolean exec() {
        // PrintStream out = new PrintStream(System.out);
        try (PrintStream out = BddUtil.newPrintStream("ActorSystem_spec.md")) {
            // Checks if all the tests are succeeded.
            List<Boolean> results = new ArrayList<Boolean>();
            results.add(newSystemSpec(out));

            out.flush();
            return BddUtil.allTrue(results);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;

//        return true;
    }


    public static boolean newSystemSpec(PrintStream out) {

        // Description
        String[] description = {
            "",
            "## `Actr::newSystem(String name)`",
            "",
            "- Given a name of an ActorSystem",
            "- When invoking the static method Actr::newSystem",
            "- Then a new ActorSystem object is created.",
            "",
            "Example:",
            "",
            "````",
            "IActorSystem system = Actr.newSystem(\"a test actor system\");",
            "String answer = system.toString();",
            "````",
            "",
            "Results:",
            "",
            };

        Arrays.stream(description)
            .forEach(out::println);

        // Reality
        ActorSystem system = new ActorSystem("system1");
        String answer = system.toString() + "\n";

        // Expectations
        String expectation = """
        {actorSystem: "system1"}
        """;



        // Check the answer.
        boolean result = BddUtil.assertTrue(out, expectation, answer);
        assert result;
        return result;

        //return true;
    }



    // public static boolean ActorOfSpec(PrintStream out) {

    //     // Description
    //     String description = """
    //         ## `Actr::actorOf(String name)`

    //         - Given an actor system object, a name of an actor, and a constructor of a POJO class
    //         - When invoking a method Actr::actorOf
    //         - Then a new ActorRef object is created in the given actor system

    //         Example:

    //         ````
    //         IActorSystem system = Actr.newSystem(\"a test actor system\");
    //         IActorRef actor = system.actorOf();
    //         ````

    //         Results:

    //         """;

    //     out.println(description);

    //     // Reality
    //     IActorSystem system = Actr.newSystem("a test actor system");
    //     String answer = system.toString();

    //     // Expectations
    //     String[] expectations = {
    //         "ActorSystem: a test actor system",
    //     };
    //     String expectation = String.join("\n",expectations);



    //     // Check the answer.
    //     boolean result = BddUtil.assertTrue(out, expectation, answer);
    //     assert result;
    //     return result;

    //     //return true;
    // }



}
