package publisher;

import subscriber.Subscriber;
import subscription.IterableSubscription;

import java.util.List;

public class IterablePublisher<T> implements Publisher<T>{

    private final List<T> items;

    public IterablePublisher(List<T> items) {
        this.items = items;
    }

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        subscriber.onSubscribe(new IterableSubscription<>(items, subscriber));
    }
}
