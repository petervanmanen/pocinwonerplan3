package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DepotTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Depot getDepotSample1() {
        return new Depot().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Depot getDepotSample2() {
        return new Depot().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Depot getDepotRandomSampleGenerator() {
        return new Depot().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString()).omschrijving(UUID.randomUUID().toString());
    }
}
