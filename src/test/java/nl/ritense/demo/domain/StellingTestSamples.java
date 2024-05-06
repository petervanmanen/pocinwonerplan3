package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StellingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Stelling getStellingSample1() {
        return new Stelling().id(1L).inhoud("inhoud1").stellingcode("stellingcode1");
    }

    public static Stelling getStellingSample2() {
        return new Stelling().id(2L).inhoud("inhoud2").stellingcode("stellingcode2");
    }

    public static Stelling getStellingRandomSampleGenerator() {
        return new Stelling()
            .id(longCount.incrementAndGet())
            .inhoud(UUID.randomUUID().toString())
            .stellingcode(UUID.randomUUID().toString());
    }
}
