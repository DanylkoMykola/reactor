package subscriber;

import subscription.Subscription;

public interface Subscriber<T> {
    void onSubscribe(Subscription item);
    void onNext(T item);
    void onError(Throwable throwable);
    void onComplete();
}
