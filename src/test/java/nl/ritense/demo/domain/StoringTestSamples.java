package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class StoringTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Storing getStoringSample1() {
        return new Storing().id(1L);
    }

    public static Storing getStoringSample2() {
        return new Storing().id(2L);
    }

    public static Storing getStoringRandomSampleGenerator() {
        return new Storing().id(longCount.incrementAndGet());
    }
}
