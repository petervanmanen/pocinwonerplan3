package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PuntengroepTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Puntengroep getPuntengroepSample1() {
        return new Puntengroep().id(1L);
    }

    public static Puntengroep getPuntengroepSample2() {
        return new Puntengroep().id(2L);
    }

    public static Puntengroep getPuntengroepRandomSampleGenerator() {
        return new Puntengroep().id(longCount.incrementAndGet());
    }
}
