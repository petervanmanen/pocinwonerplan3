package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ClassbTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Classb getClassbSample1() {
        return new Classb().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Classb getClassbSample2() {
        return new Classb().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Classb getClassbRandomSampleGenerator() {
        return new Classb().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString()).omschrijving(UUID.randomUUID().toString());
    }
}
