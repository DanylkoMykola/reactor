package publisher;

import subscriber.AsyncSubscriber;
import subscriber.PrintSubscriber;
import subscriber.Subscriber;
import subscription.Subscription;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Predicate;

public class Mono<T> implements Publisher<T> {

    private final Publisher<T> source;

    public Mono(Publisher<T> source) {
        this.source = source;
    }

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        source.subscribe(subscriber);
    }

    public void subscribe() {
        source.subscribe(new PrintSubscriber<>());
    }

    public static <T> Mono<T> just(T value) {
        return new Mono<>(new IterablePublisher<>(List.of(value)));
    }

    public Mono<T> filter(Predicate<T> predicate) {
        return new Mono<>(new FilterPublisher<>(source, predicate));
    }

    public <R> Mono<R> map(Function<T, R> mapper) {
        return new Mono<>(new MapPublisher<T, R>(source, mapper));
    }

    public Mono<T> take(long n) {
        return new Mono<>(new TakePublisher<>(source, n));
    }

    public Mono<T> subscribeOn(Executor executor) {
        return new Mono<>(subscriber -> executor.execute(() -> source.subscribe(subscriber)));
    }

    public Mono<T> publishOn(Executor executor) {
        return new Mono<>(subscriber -> source.subscribe(new AsyncSubscriber<T>(subscriber, executor)));
    }

}
