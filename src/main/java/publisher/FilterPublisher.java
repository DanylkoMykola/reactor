package publisher;

import subscriber.Subscriber;
import subscription.FilterSubscription;

import java.util.function.Predicate;

public class FilterPublisher<T> implements Publisher<T> {

    private final Publisher<T> publisher;
    private final Predicate<T> predicate;

    public FilterPublisher(Publisher<T> publisher, Predicate<T> predicate) {
        this.publisher = publisher;
        this.predicate = predicate;
    }

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        new FilterSubscription<>(publisher, subscriber, predicate);
    }
}
