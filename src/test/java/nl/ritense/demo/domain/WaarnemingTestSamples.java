package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class WaarnemingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Waarneming getWaarnemingSample1() {
        return new Waarneming().id(1L);
    }

    public static Waarneming getWaarnemingSample2() {
        return new Waarneming().id(2L);
    }

    public static Waarneming getWaarnemingRandomSampleGenerator() {
        return new Waarneming().id(longCount.incrementAndGet());
    }
}
