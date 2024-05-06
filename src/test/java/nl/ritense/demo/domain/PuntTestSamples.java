package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PuntTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Punt getPuntSample1() {
        return new Punt().id(1L).punt("punt1");
    }

    public static Punt getPuntSample2() {
        return new Punt().id(2L).punt("punt2");
    }

    public static Punt getPuntRandomSampleGenerator() {
        return new Punt().id(longCount.incrementAndGet()).punt(UUID.randomUUID().toString());
    }
}
