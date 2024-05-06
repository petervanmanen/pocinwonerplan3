package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IndienerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Indiener getIndienerSample1() {
        return new Indiener().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Indiener getIndienerSample2() {
        return new Indiener().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Indiener getIndienerRandomSampleGenerator() {
        return new Indiener().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString()).omschrijving(UUID.randomUUID().toString());
    }
}
