package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BoringTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Boring getBoringSample1() {
        return new Boring().id(1L);
    }

    public static Boring getBoringSample2() {
        return new Boring().id(2L);
    }

    public static Boring getBoringRandomSampleGenerator() {
        return new Boring().id(longCount.incrementAndGet());
    }
}
