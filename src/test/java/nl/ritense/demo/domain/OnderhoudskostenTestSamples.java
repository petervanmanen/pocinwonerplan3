package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OnderhoudskostenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Onderhoudskosten getOnderhoudskostenSample1() {
        return new Onderhoudskosten().id(1L);
    }

    public static Onderhoudskosten getOnderhoudskostenSample2() {
        return new Onderhoudskosten().id(2L);
    }

    public static Onderhoudskosten getOnderhoudskostenRandomSampleGenerator() {
        return new Onderhoudskosten().id(longCount.incrementAndGet());
    }
}
