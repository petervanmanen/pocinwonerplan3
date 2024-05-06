package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GebiedTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Gebied getGebiedSample1() {
        return new Gebied().id(1L).gebied("gebied1");
    }

    public static Gebied getGebiedSample2() {
        return new Gebied().id(2L).gebied("gebied2");
    }

    public static Gebied getGebiedRandomSampleGenerator() {
        return new Gebied().id(longCount.incrementAndGet()).gebied(UUID.randomUUID().toString());
    }
}
