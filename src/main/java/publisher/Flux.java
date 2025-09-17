package publisher;

import subscriber.AsyncSubscriber;
import subscriber.PrintSubscriber;
import subscriber.Subscriber;
import subscription.Subscription;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Flux<T> implements Publisher<T> {

    private final Publisher<T> source;

    public Flux(Publisher<T> source) {
        this.source = source;
    }

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        source.subscribe(subscriber);
    }

    public void subscribe() {
        source.subscribe(new PrintSubscriber<>());
    }

    public void subscribe(
            Consumer<? super T> onNext,
            Consumer<? super Throwable> onError,
            Runnable onComplete
    ) {
        this.subscribe(new Subscriber<T>() {
            @Override
            public void onSubscribe(Subscription s) {
                // request unbounded for demo purposes
                s.request(Long.MAX_VALUE);
            }
            @Override
            public void onNext(T item) {
                onNext.accept(item);
            }
            @Override
            public void onError(Throwable t) {
                onError.accept(t);
            }
            @Override
            public void onComplete() {
                onComplete.run();
            }
        });
    }

    public void subscribe(Consumer<? super T> onNext) {
        this.subscribe(onNext, Throwable::printStackTrace, () -> {});
    }

    public void subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
        this.subscribe(onNext, onError, () -> {});
    }

    public static <T> Flux<T> just(T... vales) {
        return new Flux<>(new IterablePublisher<>(List.of(vales)));
    }

    public Flux<T> filter(Predicate<T> predicate) {
        return new Flux<>(new FilterPublisher<>(source, predicate));
    }

    public <R> Flux<R> map(Function<T, R> mapper) {
        return new Flux<>(new MapPublisher<T, R>(source, mapper));
    }

    public Flux<T> take(long n) {
        return new Flux<>(new TakePublisher<>(source, n));
    }

    public Flux<T> subscribeOn(Executor executor) {
        return new Flux<>(subscriber -> executor.execute(() -> source.subscribe(subscriber)));
    }

    public Flux<T> publishOn(Executor executor) {
        return new Flux<>(subscriber -> source.subscribe(new AsyncSubscriber<T>(subscriber, executor)));
    }

    public static void empty() {

    }
}
