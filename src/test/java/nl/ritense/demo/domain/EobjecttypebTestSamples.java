package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class EobjecttypebTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Eobjecttypeb getEobjecttypebSample1() {
        return new Eobjecttypeb().id(1L);
    }

    public static Eobjecttypeb getEobjecttypebSample2() {
        return new Eobjecttypeb().id(2L);
    }

    public static Eobjecttypeb getEobjecttypebRandomSampleGenerator() {
        return new Eobjecttypeb().id(longCount.incrementAndGet());
    }
}
