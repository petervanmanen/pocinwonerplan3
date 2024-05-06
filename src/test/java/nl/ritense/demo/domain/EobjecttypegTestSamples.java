package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class EobjecttypegTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Eobjecttypeg getEobjecttypegSample1() {
        return new Eobjecttypeg().id(1L);
    }

    public static Eobjecttypeg getEobjecttypegSample2() {
        return new Eobjecttypeg().id(2L);
    }

    public static Eobjecttypeg getEobjecttypegRandomSampleGenerator() {
        return new Eobjecttypeg().id(longCount.incrementAndGet());
    }
}
