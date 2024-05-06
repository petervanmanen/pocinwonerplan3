package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PachterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Pachter getPachterSample1() {
        return new Pachter().id(1L);
    }

    public static Pachter getPachterSample2() {
        return new Pachter().id(2L);
    }

    public static Pachter getPachterRandomSampleGenerator() {
        return new Pachter().id(longCount.incrementAndGet());
    }
}
