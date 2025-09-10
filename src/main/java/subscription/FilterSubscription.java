package subscription;

import publisher.Publisher;
import subscriber.Subscriber;

import java.util.function.Predicate;

public class FilterSubscription<T> implements Subscription, Subscriber<T> {

    private final Predicate<T> predicate;
    private final Subscriber<T> downstream;
    private Subscription upstream;

    public FilterSubscription(Publisher<T> publisher, Subscriber<T> downstream, Predicate<T> predicate) {
        this.predicate = predicate;
        this.downstream = downstream;
        publisher.subscribe(this);
    }


    @Override
    public void onSubscribe(Subscription item) {
        this.upstream = item;
        downstream.onSubscribe(item);
    }

    @Override
    public void onNext(T item) {
        if (predicate.test(item)) {
            this.downstream.onNext(item);
        }
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
        upstream.cancel();
    }
}
