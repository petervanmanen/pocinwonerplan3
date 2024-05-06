package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class EigenaarTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Eigenaar getEigenaarSample1() {
        return new Eigenaar().id(1L);
    }

    public static Eigenaar getEigenaarSample2() {
        return new Eigenaar().id(2L);
    }

    public static Eigenaar getEigenaarRandomSampleGenerator() {
        return new Eigenaar().id(longCount.incrementAndGet());
    }
}
