package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ActiviteitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Activiteit getActiviteitSample1() {
        return new Activiteit().id(1L).omschrijving("omschrijving1");
    }

    public static Activiteit getActiviteitSample2() {
        return new Activiteit().id(2L).omschrijving("omschrijving2");
    }

    public static Activiteit getActiviteitRandomSampleGenerator() {
        return new Activiteit().id(longCount.incrementAndGet()).omschrijving(UUID.randomUUID().toString());
    }
}
