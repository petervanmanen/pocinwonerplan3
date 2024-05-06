package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BenoemdterreinTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Benoemdterrein getBenoemdterreinSample1() {
        return new Benoemdterrein().id(1L).identificatie("identificatie1");
    }

    public static Benoemdterrein getBenoemdterreinSample2() {
        return new Benoemdterrein().id(2L).identificatie("identificatie2");
    }

    public static Benoemdterrein getBenoemdterreinRandomSampleGenerator() {
        return new Benoemdterrein().id(longCount.incrementAndGet()).identificatie(UUID.randomUUID().toString());
    }
}
