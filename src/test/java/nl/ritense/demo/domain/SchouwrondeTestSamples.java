package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SchouwrondeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Schouwronde getSchouwrondeSample1() {
        return new Schouwronde().id(1L);
    }

    public static Schouwronde getSchouwrondeSample2() {
        return new Schouwronde().id(2L);
    }

    public static Schouwronde getSchouwrondeRandomSampleGenerator() {
        return new Schouwronde().id(longCount.incrementAndGet());
    }
}
