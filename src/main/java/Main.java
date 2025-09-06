import publisher.IterablePublisher;
import subscriber.PrintSubscriber;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        IterablePublisher<Integer> publisher = new IterablePublisher<>(List.of(1, 2, 3, 4, 5));
        publisher.subscribe(new PrintSubscriber<>());
    }
}
