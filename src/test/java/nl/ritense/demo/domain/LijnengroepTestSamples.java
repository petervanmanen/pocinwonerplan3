package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class LijnengroepTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Lijnengroep getLijnengroepSample1() {
        return new Lijnengroep().id(1L);
    }

    public static Lijnengroep getLijnengroepSample2() {
        return new Lijnengroep().id(2L);
    }

    public static Lijnengroep getLijnengroepRandomSampleGenerator() {
        return new Lijnengroep().id(longCount.incrementAndGet());
    }
}
