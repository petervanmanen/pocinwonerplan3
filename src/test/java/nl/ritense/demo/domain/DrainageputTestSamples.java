package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DrainageputTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Drainageput getDrainageputSample1() {
        return new Drainageput().id(1L).risicogebied("risicogebied1").type("type1");
    }

    public static Drainageput getDrainageputSample2() {
        return new Drainageput().id(2L).risicogebied("risicogebied2").type("type2");
    }

    public static Drainageput getDrainageputRandomSampleGenerator() {
        return new Drainageput()
            .id(longCount.incrementAndGet())
            .risicogebied(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
