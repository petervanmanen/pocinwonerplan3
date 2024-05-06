package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AppartementsrechtTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Appartementsrecht getAppartementsrechtSample1() {
        return new Appartementsrecht().id(1L);
    }

    public static Appartementsrecht getAppartementsrechtSample2() {
        return new Appartementsrecht().id(2L);
    }

    public static Appartementsrecht getAppartementsrechtRandomSampleGenerator() {
        return new Appartementsrecht().id(longCount.incrementAndGet());
    }
}
