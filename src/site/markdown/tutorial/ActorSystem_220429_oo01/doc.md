# Actor system

## Creating Actor system

Actor System は`newSystem`メソッドで生成し、`shutdown`メソッドで閉じる。

```java
package net.laddercode.actr.chap01.ex01;

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

```

実行結果は以下の通り。

```
$ java -cp target/laddercode-actr-fat.jar net.laddercode.actr.chap01.ex01.HelloActorSystem
ActorSystem default
$ 
```


参考: [ActorQuickStart.java](https://github.com/oogasawa/laddercode-actr/blob/main/src/example/java/com/zakgof/actr/vsakka/ActrQuickstart.java)


## `shutdown`と`shutdownCompletable`の違い



