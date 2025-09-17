package publisher;

import subscriber.PrintSubscriber;
import subscriber.Subscriber;

import java.util.List;
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

    public static void empty() {

    }
}
