package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RolTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Rol getRolSample1() {
        return new Rol().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Rol getRolSample2() {
        return new Rol().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Rol getRolRandomSampleGenerator() {
        return new Rol().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString()).omschrijving(UUID.randomUUID().toString());
    }
}
