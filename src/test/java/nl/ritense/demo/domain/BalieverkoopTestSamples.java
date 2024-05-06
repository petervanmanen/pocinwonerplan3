package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BalieverkoopTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Balieverkoop getBalieverkoopSample1() {
        return new Balieverkoop().id(1L).aantal("aantal1").kanaal("kanaal1").verkooptijd("verkooptijd1");
    }

    public static Balieverkoop getBalieverkoopSample2() {
        return new Balieverkoop().id(2L).aantal("aantal2").kanaal("kanaal2").verkooptijd("verkooptijd2");
    }

    public static Balieverkoop getBalieverkoopRandomSampleGenerator() {
        return new Balieverkoop()
            .id(longCount.incrementAndGet())
            .aantal(UUID.randomUUID().toString())
            .kanaal(UUID.randomUUID().toString())
            .verkooptijd(UUID.randomUUID().toString());
    }
}
