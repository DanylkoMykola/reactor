package publisher;

import subscriber.Subscriber;

public interface Publisher<T> {
    void subscribe(Subscriber<T> subscriber);
}
