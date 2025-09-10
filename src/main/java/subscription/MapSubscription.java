package subscription;

import publisher.Publisher;
import subscriber.Subscriber;

import java.util.function.Function;

public class MapSubscription<T, R> implements Subscription, Subscriber<T> {

    private final Subscriber<R> downstream;
    private final Function<T, R> mapper;
    private Subscription upstream;


    public MapSubscription(Publisher<T> publisher, Subscriber<R> downstream, Function<T, R> mapper) {
        this.downstream = downstream;
        this.mapper = mapper;
        publisher.subscribe(this);
    }

    @Override
    public void onSubscribe(Subscription item) {
        this.upstream = item;
        downstream.onSubscribe(this);
    }

    @Override
    public void onNext(T item) {
        R mapped = mapper.apply(item);
        this.downstream.onNext(mapped);
    }

    @Override
    public void onError(Throwable throwable) {
        downstream.onError(throwable);
    }

    @Override
    public void onComplete() {
        downstream.onComplete();
    }

    @Override
    public void request(long n) {
        upstream.request(n);
    }

    @Override
    public void cancel() {

    }
}
