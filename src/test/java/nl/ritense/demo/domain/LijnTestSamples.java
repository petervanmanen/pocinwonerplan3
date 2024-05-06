package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LijnTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Lijn getLijnSample1() {
        return new Lijn().id(1L).lijn("lijn1");
    }

    public static Lijn getLijnSample2() {
        return new Lijn().id(2L).lijn("lijn2");
    }

    public static Lijn getLijnRandomSampleGenerator() {
        return new Lijn().id(longCount.incrementAndGet()).lijn(UUID.randomUUID().toString());
    }
}
