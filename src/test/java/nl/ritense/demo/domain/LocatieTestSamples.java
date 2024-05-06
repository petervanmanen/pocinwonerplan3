package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LocatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Locatie getLocatieSample1() {
        return new Locatie().id(1L).adres("adres1");
    }

    public static Locatie getLocatieSample2() {
        return new Locatie().id(2L).adres("adres2");
    }

    public static Locatie getLocatieRandomSampleGenerator() {
        return new Locatie().id(longCount.incrementAndGet()).adres(UUID.randomUUID().toString());
    }
}
