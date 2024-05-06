package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CultuurcodeonbebouwdTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Cultuurcodeonbebouwd getCultuurcodeonbebouwdSample1() {
        return new Cultuurcodeonbebouwd()
            .id(1L)
            .cultuurcodeonbebouwd("cultuurcodeonbebouwd1")
            .naamcultuurcodeonbebouwd("naamcultuurcodeonbebouwd1");
    }

    public static Cultuurcodeonbebouwd getCultuurcodeonbebouwdSample2() {
        return new Cultuurcodeonbebouwd()
            .id(2L)
            .cultuurcodeonbebouwd("cultuurcodeonbebouwd2")
            .naamcultuurcodeonbebouwd("naamcultuurcodeonbebouwd2");
    }

    public static Cultuurcodeonbebouwd getCultuurcodeonbebouwdRandomSampleGenerator() {
        return new Cultuurcodeonbebouwd()
            .id(longCount.incrementAndGet())
            .cultuurcodeonbebouwd(UUID.randomUUID().toString())
            .naamcultuurcodeonbebouwd(UUID.randomUUID().toString());
    }
}
