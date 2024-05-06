package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class EobjecttypeeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Eobjecttypee getEobjecttypeeSample1() {
        return new Eobjecttypee().id(1L);
    }

    public static Eobjecttypee getEobjecttypeeSample2() {
        return new Eobjecttypee().id(2L);
    }

    public static Eobjecttypee getEobjecttypeeRandomSampleGenerator() {
        return new Eobjecttypee().id(longCount.incrementAndGet());
    }
}
