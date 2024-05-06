package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class EobjecttypedTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Eobjecttyped getEobjecttypedSample1() {
        return new Eobjecttyped().id(1L);
    }

    public static Eobjecttyped getEobjecttypedSample2() {
        return new Eobjecttyped().id(2L);
    }

    public static Eobjecttyped getEobjecttypedRandomSampleGenerator() {
        return new Eobjecttyped().id(longCount.incrementAndGet());
    }
}
