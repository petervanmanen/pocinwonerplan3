package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KeermuurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Keermuur getKeermuurSample1() {
        return new Keermuur().id(1L).belastingklassenieuw("belastingklassenieuw1").belastingklasseoud("belastingklasseoud1").type("type1");
    }

    public static Keermuur getKeermuurSample2() {
        return new Keermuur().id(2L).belastingklassenieuw("belastingklassenieuw2").belastingklasseoud("belastingklasseoud2").type("type2");
    }

    public static Keermuur getKeermuurRandomSampleGenerator() {
        return new Keermuur()
            .id(longCount.incrementAndGet())
            .belastingklassenieuw(UUID.randomUUID().toString())
            .belastingklasseoud(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
