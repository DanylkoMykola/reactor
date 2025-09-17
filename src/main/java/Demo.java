import publisher.FilterPublisher;
import publisher.Flux;
import publisher.IterablePublisher;
import publisher.MapPublisher;
import publisher.Mono;
import publisher.Publisher;
import publisher.TakePublisher;
import subscriber.PrintSubscriber;

import java.util.List;
import java.util.concurrent.Executors;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        IterablePublisher<Integer> iterablePublisher = new IterablePublisher<>(List.of(1, 2, 3, 4, 5));

        Publisher<Integer> filtered = new FilterPublisher<>(iterablePublisher, i -> i % 2 == 0);
        Publisher<String> mapped = new MapPublisher<>(filtered, i -> "Number " + i);
        mapped.subscribe(new PrintSubscriber<>());

        TakePublisher<Integer> takePublisher = new TakePublisher<>(iterablePublisher, 3);
        takePublisher.subscribe(new PrintSubscriber<>());

        Mono.just(10).subscribe();


        var pool1 = Executors.newSingleThreadExecutor(r -> new Thread(r, "pool1"));
        var pool2 = Executors.newSingleThreadExecutor(r -> new Thread(r, "pool2"));

        Flux.just(1, 2, 3, 4, 5)
                .subscribeOn(pool1)     // subscription starts on pool1
                .publishOn(pool2)       // onNext signals are delivered on pool2
                .subscribe(
                        v -> System.out.println(Thread.currentThread().getName() + " got " + v),
                        err -> err.printStackTrace(),
                        () -> {
                            System.out.println("done on " + Thread.currentThread().getName());
                            pool1.shutdown();
                            pool2.shutdown();
                        }
                );

    }
}

