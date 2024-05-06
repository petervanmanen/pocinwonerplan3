package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BelproviderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Belprovider getBelproviderSample1() {
        return new Belprovider().id(1L).code("code1");
    }

    public static Belprovider getBelproviderSample2() {
        return new Belprovider().id(2L).code("code2");
    }

    public static Belprovider getBelproviderRandomSampleGenerator() {
        return new Belprovider().id(longCount.incrementAndGet()).code(UUID.randomUUID().toString());
    }
}
