package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class EobjecttypeaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Eobjecttypea getEobjecttypeaSample1() {
        return new Eobjecttypea().id(1L);
    }

    public static Eobjecttypea getEobjecttypeaSample2() {
        return new Eobjecttypea().id(2L);
    }

    public static Eobjecttypea getEobjecttypeaRandomSampleGenerator() {
        return new Eobjecttypea().id(longCount.incrementAndGet());
    }
}
