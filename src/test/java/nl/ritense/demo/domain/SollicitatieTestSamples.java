package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SollicitatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sollicitatie getSollicitatieSample1() {
        return new Sollicitatie().id(1L);
    }

    public static Sollicitatie getSollicitatieSample2() {
        return new Sollicitatie().id(2L);
    }

    public static Sollicitatie getSollicitatieRandomSampleGenerator() {
        return new Sollicitatie().id(longCount.incrementAndGet());
    }
}
