package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AantalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aantal getAantalSample1() {
        return new Aantal().id(1L).aantal("aantal1");
    }

    public static Aantal getAantalSample2() {
        return new Aantal().id(2L).aantal("aantal2");
    }

    public static Aantal getAantalRandomSampleGenerator() {
        return new Aantal().id(longCount.incrementAndGet()).aantal(UUID.randomUUID().toString());
    }
}
