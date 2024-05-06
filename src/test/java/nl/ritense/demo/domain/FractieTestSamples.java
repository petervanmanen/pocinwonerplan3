package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FractieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Fractie getFractieSample1() {
        return new Fractie().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Fractie getFractieSample2() {
        return new Fractie().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Fractie getFractieRandomSampleGenerator() {
        return new Fractie().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString()).omschrijving(UUID.randomUUID().toString());
    }
}
