package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class WerkbonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Werkbon getWerkbonSample1() {
        return new Werkbon().id(1L);
    }

    public static Werkbon getWerkbonSample2() {
        return new Werkbon().id(2L);
    }

    public static Werkbon getWerkbonRandomSampleGenerator() {
        return new Werkbon().id(longCount.incrementAndGet());
    }
}
