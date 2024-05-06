package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GeluidsschermTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Geluidsscherm getGeluidsschermSample1() {
        return new Geluidsscherm().id(1L).aantaldeuren("aantaldeuren1").aantalpanelen("aantalpanelen1").type("type1");
    }

    public static Geluidsscherm getGeluidsschermSample2() {
        return new Geluidsscherm().id(2L).aantaldeuren("aantaldeuren2").aantalpanelen("aantalpanelen2").type("type2");
    }

    public static Geluidsscherm getGeluidsschermRandomSampleGenerator() {
        return new Geluidsscherm()
            .id(longCount.incrementAndGet())
            .aantaldeuren(UUID.randomUUID().toString())
            .aantalpanelen(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
