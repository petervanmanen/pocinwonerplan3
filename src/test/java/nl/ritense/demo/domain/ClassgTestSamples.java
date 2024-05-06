package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ClassgTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Classg getClassgSample1() {
        return new Classg().id(1L);
    }

    public static Classg getClassgSample2() {
        return new Classg().id(2L);
    }

    public static Classg getClassgRandomSampleGenerator() {
        return new Classg().id(longCount.incrementAndGet());
    }
}
