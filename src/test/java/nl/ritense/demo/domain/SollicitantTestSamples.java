package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SollicitantTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sollicitant getSollicitantSample1() {
        return new Sollicitant().id(1L);
    }

    public static Sollicitant getSollicitantSample2() {
        return new Sollicitant().id(2L);
    }

    public static Sollicitant getSollicitantRandomSampleGenerator() {
        return new Sollicitant().id(longCount.incrementAndGet());
    }
}
