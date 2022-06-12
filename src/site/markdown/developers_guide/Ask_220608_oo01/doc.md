


通常使う`ask`メソッドの定義は以下の通り。

```java
@Override
public <R> CompletableFuture<R> ask(Function<T, R> action) {
    return ask((target, callback) -> callback.accept(action.apply(target)));
}
```

この`return`文で使っている`ask`メソッドは以下のとおりである。


