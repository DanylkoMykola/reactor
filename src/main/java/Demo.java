import publisher.IterablePublisher;
import publisher.MapPublisher;
import publisher.Publisher;
import subscriber.PrintSubscriber;

import java.util.List;

public class Demo {
    public static void main(String[] args) {
        IterablePublisher<Integer> iterablePublisher = new IterablePublisher<>(List.of(1, 2, 3, 4, 5));

        Publisher<String>  mapped = new MapPublisher<>(iterablePublisher, i -> "NUmber " + i);
        mapped.subscribe(new PrintSubscriber<>());
    }
}
