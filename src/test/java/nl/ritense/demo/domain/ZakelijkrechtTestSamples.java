package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ZakelijkrechtTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Zakelijkrecht getZakelijkrechtSample1() {
        return new Zakelijkrecht().id(1L).soort("soort1");
    }

    public static Zakelijkrecht getZakelijkrechtSample2() {
        return new Zakelijkrecht().id(2L).soort("soort2");
    }

    public static Zakelijkrecht getZakelijkrechtRandomSampleGenerator() {
        return new Zakelijkrecht().id(longCount.incrementAndGet()).soort(UUID.randomUUID().toString());
    }
}
