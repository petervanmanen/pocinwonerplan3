package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OnderwerpTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Onderwerp getOnderwerpSample1() {
        return new Onderwerp().id(1L);
    }

    public static Onderwerp getOnderwerpSample2() {
        return new Onderwerp().id(2L);
    }

    public static Onderwerp getOnderwerpRandomSampleGenerator() {
        return new Onderwerp().id(longCount.incrementAndGet());
    }
}
