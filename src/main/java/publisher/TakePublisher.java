package publisher;

import subscriber.Subscriber;
import subscription.TaskSubscription;

public class TakePublisher<T> implements Publisher<T> {

    private final Publisher<T> publisher;
    private long limit = 0;

    public TakePublisher(Publisher<T> publisher, long limit) {
        this.publisher = publisher;
        this.limit = limit;
    }

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        new TaskSubscription<>(publisher, subscriber, limit);
    }
}
