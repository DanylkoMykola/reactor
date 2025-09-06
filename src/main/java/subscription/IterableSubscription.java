package subscription;

import subscriber.Subscriber;

import java.util.List;

public class IterableSubscription<T> implements Subscription {

    private final List<T> items;
    private final Subscriber<T> subscriber;

    public IterableSubscription(List<T> items, Subscriber<T> subscriber) {
        this.items = items;
        this.subscriber = subscriber;
    }

    @Override
    public void request(long n) {
        items.forEach(subscriber::onNext);
        subscriber.onComplete();
    }

    @Override
    public void cancel() {

    }
}
