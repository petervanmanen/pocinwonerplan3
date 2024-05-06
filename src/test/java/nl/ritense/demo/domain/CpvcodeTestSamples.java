package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CpvcodeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Cpvcode getCpvcodeSample1() {
        return new Cpvcode().id(1L).code("code1").omschrijving("omschrijving1");
    }

    public static Cpvcode getCpvcodeSample2() {
        return new Cpvcode().id(2L).code("code2").omschrijving("omschrijving2");
    }

    public static Cpvcode getCpvcodeRandomSampleGenerator() {
        return new Cpvcode().id(longCount.incrementAndGet()).code(UUID.randomUUID().toString()).omschrijving(UUID.randomUUID().toString());
    }
}
