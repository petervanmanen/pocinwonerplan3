package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MjopTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Mjop getMjopSample1() {
        return new Mjop().id(1L).omschrijving("omschrijving1");
    }

    public static Mjop getMjopSample2() {
        return new Mjop().id(2L).omschrijving("omschrijving2");
    }

    public static Mjop getMjopRandomSampleGenerator() {
        return new Mjop().id(longCount.incrementAndGet()).omschrijving(UUID.randomUUID().toString());
    }
}
