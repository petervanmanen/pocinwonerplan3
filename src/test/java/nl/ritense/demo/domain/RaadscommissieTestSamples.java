package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RaadscommissieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Raadscommissie getRaadscommissieSample1() {
        return new Raadscommissie().id(1L).naam("naam1");
    }

    public static Raadscommissie getRaadscommissieSample2() {
        return new Raadscommissie().id(2L).naam("naam2");
    }

    public static Raadscommissie getRaadscommissieRandomSampleGenerator() {
        return new Raadscommissie().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
