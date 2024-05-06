package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProducttypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Producttype getProducttypeSample1() {
        return new Producttype().id(1L).omschrijving("omschrijving1");
    }

    public static Producttype getProducttypeSample2() {
        return new Producttype().id(2L).omschrijving("omschrijving2");
    }

    public static Producttype getProducttypeRandomSampleGenerator() {
        return new Producttype().id(longCount.incrementAndGet()).omschrijving(UUID.randomUUID().toString());
    }
}
