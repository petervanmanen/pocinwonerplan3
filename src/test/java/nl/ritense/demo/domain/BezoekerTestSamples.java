package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BezoekerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bezoeker getBezoekerSample1() {
        return new Bezoeker().id(1L);
    }

    public static Bezoeker getBezoekerSample2() {
        return new Bezoeker().id(2L);
    }

    public static Bezoeker getBezoekerRandomSampleGenerator() {
        return new Bezoeker().id(longCount.incrementAndGet());
    }
}
