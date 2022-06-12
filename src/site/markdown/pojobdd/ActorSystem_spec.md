
## `Actr::newSystem(String name)`

- Given a name of an ActorSystem
- When invoking a static method Actr::newSystem
- Then a new ActorSystem object is created.

Example:

````
IActorSystem system = Actr.newSystem("a test actor system");
String answer = system.toString();
````

Results:

````
ActorSystem: a test actor system
````
