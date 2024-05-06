package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ActivaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Activa getActivaSample1() {
        return new Activa().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Activa getActivaSample2() {
        return new Activa().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Activa getActivaRandomSampleGenerator() {
        return new Activa().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString()).omschrijving(UUID.randomUUID().toString());
    }
}
