
# Introduction

## Actor Model とは

並列計算プログラムはロジックを書くのが難しくバグが入りやすいが、Actor Model はこの欠点を緩和する方法の一つである。

Actor Model の特徴は以下の３点である(`bauer2018actor4j`)

> In classic concurrent programming, it goes over the
> concepts of shared state, mutual exclusion and semaphores.
> [17]. With increasing program complexity, the correctness
> of the program is difficult to proof or to verify. Especially,
> because concurrent programs are difficult to test [18].
> However, the actor model, based on message passing, offers
> an alternative. The essential features compared to the classic
> concurrent programming are:

- no shared state,
- asynchronous message transfer, and
- message queue for each actor [17].


参考資料

```
@inproceedings{bauer2018actor4j,
  title={Actor4j: a software framework for the actor model focusing on the optimization of message passing},
  author={Bauer, David Alessandro and M{\"a}ki{\"o}, Juho},
  booktitle={AICT 2018: The Fourteenth Advanced International Conference on Telecommunications},
  year={2018},
  organization={IARIA}
}
```


### Actor Model の特徴と応用先

https://tech-lab.sios.jp/archives/8738
によると、大規模な Web アプリケーションへの応用において、RabbitMQ のような方法ではメッセージブローカーでメッセージを集中管理するので、システムが大規模になったときにここがボトルネックになるが、Actor Model ではメッセージは各アクターが処理するのでシステムが大規模になっても問題が起こりにくいことを挙げている。


<img src="../../images/2022-04-24_17-39.png" width="500" />
<img src="../../images/2022-04-24_17-39_1.png" width="500" />



## Java による Actor Model のフレームワーク

Java による Actor Model のライブラリは Akka が有名であるが、他にも以下のような種類がある。

- akka
- [actor4j](https://actor4j.io)
- kilim
- quasar
- reactors.io
- orbit
- offbynull/actors
- edescourtis/actor
- vlingo-actors
- pcd-actors

参考資料

- [Type-safe actor model for Java (2020)](https://medium.com/@zakgof/type-safe-actor-model-for-java-7133857a9f72)



## `actr`ライブラリの特徴

- 有名な Akka は Scala で書かれているが、`actr`ライブラリは Java で書かれている。
- Akka などのライブラリは旧式の Java で書かれてるが、`actr`は Modern Java で書かれている。
    - したがってコードは小さいが標準の API の機能で実装されているので、むしろ情報が多くわかりやすい。
- Comedy.js と同様に POJO を Actor としてつかう構成になっている。
    - Akka では Actor は特定のクラスのサブクラスである必要がある。
- とても小さなライブラリ(2 千行弱）なので自前で管理可能。
- 無駄に複雑な機能がない分、拡張がしやすい。


参考資料

- [Type-safe actor model for Java (2020)](https://medium.com/@zakgof/type-safe-actor-model-for-java-7133857a9f72)


## `actr`ライブラリの基本構造

基本構造は以下のようになっている。（Java の標準 API を使っているのでライブラリが簡潔に書けている。）

- `actr`ライブラリでは Actor へのメッセージが`Runnable`, `Callable`として実装される。
- そうするとこれを実行するための`Executor`が要る。
    - `IActorSystem`のインスタンス(実際には`ActorSystemImp`のインスタンス)を作るときに、
    `IActorScheduler`のインスタンスが作られる、またはすでに作ってあるインスタンスを`IActorSystem`に渡す。
    いずれにしろここで`IActorSystem`は`IActorScheduler`を持った状態になる。
- Actor は実際のところ`com.zakgof.actr.impl.ActorImpl`クラスで定義されている。
    - ここを見ると、各 Actor は(1)POJO (2) actor system (3) actor scheduler を持っていることがわかる。
    

したがって、各クラスの基本的な役割は以下のとおりである。

- Actor へのメッセージがこの actor scheduler の上で実行される。
- ActorSystem は、この actor scheduler を shutdown するなどの管理を行う。


## `actor4j`ライブラリの特徴



参考資料

- [actor4j 公式サイト](https://actor4j.io)

```
@inproceedings{bauer2018actor4j,
  title={Actor4j: a software framework for the actor model focusing on the optimization of message passing},
  author={Bauer, David Alessandro and M{\"a}ki{\"o}, Juho},
  booktitle={AICT 2018: The Fourteenth Advanced International Conference on Telecommunications},
  year={2018},
  organization={IARIA}
}
```

