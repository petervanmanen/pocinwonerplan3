package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class EobjecttypefTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Eobjecttypef getEobjecttypefSample1() {
        return new Eobjecttypef().id(1L);
    }

    public static Eobjecttypef getEobjecttypefSample2() {
        return new Eobjecttypef().id(2L);
    }

    public static Eobjecttypef getEobjecttypefRandomSampleGenerator() {
        return new Eobjecttypef().id(longCount.incrementAndGet());
    }
}
