package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PipTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Pip getPipSample1() {
        return new Pip().id(1L);
    }

    public static Pip getPipSample2() {
        return new Pip().id(2L);
    }

    public static Pip getPipRandomSampleGenerator() {
        return new Pip().id(longCount.incrementAndGet());
    }
}
