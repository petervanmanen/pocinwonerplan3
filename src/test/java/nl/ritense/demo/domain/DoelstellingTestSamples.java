package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DoelstellingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Doelstelling getDoelstellingSample1() {
        return new Doelstelling().id(1L).omschrijving("omschrijving1");
    }

    public static Doelstelling getDoelstellingSample2() {
        return new Doelstelling().id(2L).omschrijving("omschrijving2");
    }

    public static Doelstelling getDoelstellingRandomSampleGenerator() {
        return new Doelstelling().id(longCount.incrementAndGet()).omschrijving(UUID.randomUUID().toString());
    }
}
