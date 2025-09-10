package publisher;

import subscriber.Subscriber;
import subscription.MapSubscription;

import java.util.function.Function;

public class MapPublisher<T, R> implements Publisher<R> {

    private final Publisher<T> publisher;
    private Function<T, R> mapper;

    public MapPublisher(Publisher<T> publisher, Function<T, R> mapper) {
        this.publisher = publisher;
        this.mapper = mapper;
    }

    @Override
    public void subscribe(Subscriber<R> subscriber) {
        new MapSubscription<>(publisher, subscriber, mapper);
    }
}
