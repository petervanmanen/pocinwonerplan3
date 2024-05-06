package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BijzonderheidTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bijzonderheid getBijzonderheidSample1() {
        return new Bijzonderheid().id(1L).omschrijving("omschrijving1");
    }

    public static Bijzonderheid getBijzonderheidSample2() {
        return new Bijzonderheid().id(2L).omschrijving("omschrijving2");
    }

    public static Bijzonderheid getBijzonderheidRandomSampleGenerator() {
        return new Bijzonderheid().id(longCount.incrementAndGet()).omschrijving(UUID.randomUUID().toString());
    }
}
