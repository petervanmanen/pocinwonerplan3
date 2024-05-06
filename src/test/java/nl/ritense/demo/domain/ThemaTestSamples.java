package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ThemaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Thema getThemaSample1() {
        return new Thema().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Thema getThemaSample2() {
        return new Thema().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Thema getThemaRandomSampleGenerator() {
        return new Thema().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString()).omschrijving(UUID.randomUUID().toString());
    }
}
