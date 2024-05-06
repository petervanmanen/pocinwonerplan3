package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MutatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Mutatie getMutatieSample1() {
        return new Mutatie().id(1L).datum("datum1");
    }

    public static Mutatie getMutatieSample2() {
        return new Mutatie().id(2L).datum("datum2");
    }

    public static Mutatie getMutatieRandomSampleGenerator() {
        return new Mutatie().id(longCount.incrementAndGet()).datum(UUID.randomUUID().toString());
    }
}
