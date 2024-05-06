package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ClassdTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Classd getClassdSample1() {
        return new Classd().id(1L);
    }

    public static Classd getClassdSample2() {
        return new Classd().id(2L);
    }

    public static Classd getClassdRandomSampleGenerator() {
        return new Classd().id(longCount.incrementAndGet());
    }
}
