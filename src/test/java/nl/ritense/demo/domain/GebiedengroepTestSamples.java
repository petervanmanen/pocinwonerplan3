package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class GebiedengroepTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Gebiedengroep getGebiedengroepSample1() {
        return new Gebiedengroep().id(1L);
    }

    public static Gebiedengroep getGebiedengroepSample2() {
        return new Gebiedengroep().id(2L);
    }

    public static Gebiedengroep getGebiedengroepRandomSampleGenerator() {
        return new Gebiedengroep().id(longCount.incrementAndGet());
    }
}
