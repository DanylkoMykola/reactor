package subscriber;

import publisher.Publisher;
import subscription.Subscription;

import java.util.concurrent.Executor;

public class AsyncSubscriber<T> implements Subscriber<T> {

    private final Subscriber<T> subscriber;
    private final Executor executor;

    public AsyncSubscriber(Subscriber<T> subscriber, Executor executor) {
        this.subscriber = subscriber;
        this.executor = executor;
    }

    @Override
    public void onSubscribe(Subscription item) {
        subscriber.onSubscribe(item);
    }

    @Override
    public void onNext(T item) {
        executor.execute(() -> subscriber.onNext(item));
    }

    @Override
    public void onError(Throwable throwable) {
        executor.execute(() -> subscriber.onError(throwable));
    }

    @Override
    public void onComplete() {
        executor.execute(subscriber::onComplete);
    }
}
