package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PrecarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Precario getPrecarioSample1() {
        return new Precario().id(1L);
    }

    public static Precario getPrecarioSample2() {
        return new Precario().id(2L);
    }

    public static Precario getPrecarioRandomSampleGenerator() {
        return new Precario().id(longCount.incrementAndGet());
    }
}
