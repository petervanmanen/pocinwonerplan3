package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VreemdelingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vreemdeling getVreemdelingSample1() {
        return new Vreemdeling().id(1L).sociaalreferent("sociaalreferent1").vnummer("vnummer1");
    }

    public static Vreemdeling getVreemdelingSample2() {
        return new Vreemdeling().id(2L).sociaalreferent("sociaalreferent2").vnummer("vnummer2");
    }

    public static Vreemdeling getVreemdelingRandomSampleGenerator() {
        return new Vreemdeling()
            .id(longCount.incrementAndGet())
            .sociaalreferent(UUID.randomUUID().toString())
            .vnummer(UUID.randomUUID().toString());
    }
}
