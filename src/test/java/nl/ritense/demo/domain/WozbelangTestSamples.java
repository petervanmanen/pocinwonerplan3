package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WozbelangTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Wozbelang getWozbelangSample1() {
        return new Wozbelang().id(1L).eigenaargebruiker("eigenaargebruiker1");
    }

    public static Wozbelang getWozbelangSample2() {
        return new Wozbelang().id(2L).eigenaargebruiker("eigenaargebruiker2");
    }

    public static Wozbelang getWozbelangRandomSampleGenerator() {
        return new Wozbelang().id(longCount.incrementAndGet()).eigenaargebruiker(UUID.randomUUID().toString());
    }
}
