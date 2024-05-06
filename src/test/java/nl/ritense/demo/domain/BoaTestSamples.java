package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BoaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Boa getBoaSample1() {
        return new Boa().id(1L);
    }

    public static Boa getBoaSample2() {
        return new Boa().id(2L);
    }

    public static Boa getBoaRandomSampleGenerator() {
        return new Boa().id(longCount.incrementAndGet());
    }
}
