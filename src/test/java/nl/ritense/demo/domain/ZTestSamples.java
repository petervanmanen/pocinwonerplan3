package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ZTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Z getZSample1() {
        return new Z().id(1L);
    }

    public static Z getZSample2() {
        return new Z().id(2L);
    }

    public static Z getZRandomSampleGenerator() {
        return new Z().id(longCount.incrementAndGet());
    }
}
