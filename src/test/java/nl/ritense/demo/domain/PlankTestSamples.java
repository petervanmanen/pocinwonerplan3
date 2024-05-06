package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PlankTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Plank getPlankSample1() {
        return new Plank().id(1L).planknummer("planknummer1");
    }

    public static Plank getPlankSample2() {
        return new Plank().id(2L).planknummer("planknummer2");
    }

    public static Plank getPlankRandomSampleGenerator() {
        return new Plank().id(longCount.incrementAndGet()).planknummer(UUID.randomUUID().toString());
    }
}
