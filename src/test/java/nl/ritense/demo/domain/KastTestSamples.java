package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KastTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kast getKastSample1() {
        return new Kast().id(1L).kastnummer("kastnummer1");
    }

    public static Kast getKastSample2() {
        return new Kast().id(2L).kastnummer("kastnummer2");
    }

    public static Kast getKastRandomSampleGenerator() {
        return new Kast().id(longCount.incrementAndGet()).kastnummer(UUID.randomUUID().toString());
    }
}
