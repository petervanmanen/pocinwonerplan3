package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BevoegdgezagTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bevoegdgezag getBevoegdgezagSample1() {
        return new Bevoegdgezag().id(1L).naam("naam1");
    }

    public static Bevoegdgezag getBevoegdgezagSample2() {
        return new Bevoegdgezag().id(2L).naam("naam2");
    }

    public static Bevoegdgezag getBevoegdgezagRandomSampleGenerator() {
        return new Bevoegdgezag().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
