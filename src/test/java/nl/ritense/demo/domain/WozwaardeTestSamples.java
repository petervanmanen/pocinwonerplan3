package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WozwaardeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Wozwaarde getWozwaardeSample1() {
        return new Wozwaarde().id(1L).statusbeschikking("statusbeschikking1").vastgesteldewaarde("vastgesteldewaarde1");
    }

    public static Wozwaarde getWozwaardeSample2() {
        return new Wozwaarde().id(2L).statusbeschikking("statusbeschikking2").vastgesteldewaarde("vastgesteldewaarde2");
    }

    public static Wozwaarde getWozwaardeRandomSampleGenerator() {
        return new Wozwaarde()
            .id(longCount.incrementAndGet())
            .statusbeschikking(UUID.randomUUID().toString())
            .vastgesteldewaarde(UUID.randomUUID().toString());
    }
}
