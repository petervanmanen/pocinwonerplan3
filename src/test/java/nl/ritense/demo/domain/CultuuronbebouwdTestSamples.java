package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CultuuronbebouwdTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Cultuuronbebouwd getCultuuronbebouwdSample1() {
        return new Cultuuronbebouwd().id(1L).cultuurcodeonbebouwd("cultuurcodeonbebouwd1");
    }

    public static Cultuuronbebouwd getCultuuronbebouwdSample2() {
        return new Cultuuronbebouwd().id(2L).cultuurcodeonbebouwd("cultuurcodeonbebouwd2");
    }

    public static Cultuuronbebouwd getCultuuronbebouwdRandomSampleGenerator() {
        return new Cultuuronbebouwd().id(longCount.incrementAndGet()).cultuurcodeonbebouwd(UUID.randomUUID().toString());
    }
}
