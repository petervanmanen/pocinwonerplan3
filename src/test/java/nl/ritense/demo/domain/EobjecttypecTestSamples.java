package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class EobjecttypecTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Eobjecttypec getEobjecttypecSample1() {
        return new Eobjecttypec().id(1L);
    }

    public static Eobjecttypec getEobjecttypecSample2() {
        return new Eobjecttypec().id(2L);
    }

    public static Eobjecttypec getEobjecttypecRandomSampleGenerator() {
        return new Eobjecttypec().id(longCount.incrementAndGet());
    }
}
