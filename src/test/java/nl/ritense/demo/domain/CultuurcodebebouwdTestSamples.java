package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CultuurcodebebouwdTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Cultuurcodebebouwd getCultuurcodebebouwdSample1() {
        return new Cultuurcodebebouwd().id(1L).cultuurcodebebouwd("cultuurcodebebouwd1").naamcultuurcodebebouwd("naamcultuurcodebebouwd1");
    }

    public static Cultuurcodebebouwd getCultuurcodebebouwdSample2() {
        return new Cultuurcodebebouwd().id(2L).cultuurcodebebouwd("cultuurcodebebouwd2").naamcultuurcodebebouwd("naamcultuurcodebebouwd2");
    }

    public static Cultuurcodebebouwd getCultuurcodebebouwdRandomSampleGenerator() {
        return new Cultuurcodebebouwd()
            .id(longCount.incrementAndGet())
            .cultuurcodebebouwd(UUID.randomUUID().toString())
            .naamcultuurcodebebouwd(UUID.randomUUID().toString());
    }
}
