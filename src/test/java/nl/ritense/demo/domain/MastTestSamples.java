package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class MastTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Mast getMastSample1() {
        return new Mast().id(1L);
    }

    public static Mast getMastSample2() {
        return new Mast().id(2L);
    }

    public static Mast getMastRandomSampleGenerator() {
        return new Mast().id(longCount.incrementAndGet());
    }
}
