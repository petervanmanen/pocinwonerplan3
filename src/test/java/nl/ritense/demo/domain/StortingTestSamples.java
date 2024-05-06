package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StortingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Storting getStortingSample1() {
        return new Storting().id(1L).datumtijd("datumtijd1").gewicht("gewicht1");
    }

    public static Storting getStortingSample2() {
        return new Storting().id(2L).datumtijd("datumtijd2").gewicht("gewicht2");
    }

    public static Storting getStortingRandomSampleGenerator() {
        return new Storting().id(longCount.incrementAndGet()).datumtijd(UUID.randomUUID().toString()).gewicht(UUID.randomUUID().toString());
    }
}
