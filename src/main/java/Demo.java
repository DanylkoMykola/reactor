import publisher.FilterPublisher;
import publisher.IterablePublisher;
import publisher.MapPublisher;
import publisher.Publisher;
import publisher.TakePublisher;
import subscriber.PrintSubscriber;

import java.util.List;

public class Demo {
    public static void main(String[] args) {
        IterablePublisher<Integer> iterablePublisher = new IterablePublisher<>(List.of(1, 2, 3, 4, 5));

        Publisher<Integer> filtered = new FilterPublisher<>(iterablePublisher, i -> i % 2 == 0);
        Publisher<String> mapped = new MapPublisher<>(filtered, i -> "Number " + i);
        mapped.subscribe(new PrintSubscriber<>());

        TakePublisher<Integer> takePublisher = new TakePublisher<>(iterablePublisher, 3);
        takePublisher.subscribe(new PrintSubscriber<>());
    }
}
