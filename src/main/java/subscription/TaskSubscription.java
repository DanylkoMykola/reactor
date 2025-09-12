package subscription;

import publisher.Publisher;
import subscriber.Subscriber;

public class TaskSubscription<T> implements Subscription, Subscriber<T> {

    private final Subscriber<T> downstream;
    private long limit;
    private Subscription upstream;
    private long count = 0;
    private boolean isDone = false;

    public TaskSubscription(Publisher<T> publisher, Subscriber<T> downstream, long limit) {
        this.downstream = downstream;
        this.limit = limit;
        publisher.subscribe(this);
    }

    @Override
    public void onSubscribe(Subscription item) {
        this.upstream = item;
        downstream.onSubscribe(this);
    }

    @Override
    public void onNext(T item) {
        if (!isDone) {
            if (count < limit) {
                downstream.onNext(item);
                count++;
                if (count == limit) {
                    isDone = true;
                    onComplete();
                }
            }
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
        if (!isDone) {
            long remaining = limit - count;
            if (remaining > 0) {
                upstream.request(Math.min(n, remaining));
            }
        }
    }

    @Override
    public void cancel() {
        upstream.cancel();
    }
}
