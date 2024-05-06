package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class KponstaanuitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kponstaanuit getKponstaanuitSample1() {
        return new Kponstaanuit().id(1L);
    }

    public static Kponstaanuit getKponstaanuitSample2() {
        return new Kponstaanuit().id(2L);
    }

    public static Kponstaanuit getKponstaanuitRandomSampleGenerator() {
        return new Kponstaanuit().id(longCount.incrementAndGet());
    }
}
