package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GebruiksdoelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Gebruiksdoel getGebruiksdoelSample1() {
        return new Gebruiksdoel().id(1L).gebruiksdoelgebouwdobject("gebruiksdoelgebouwdobject1");
    }

    public static Gebruiksdoel getGebruiksdoelSample2() {
        return new Gebruiksdoel().id(2L).gebruiksdoelgebouwdobject("gebruiksdoelgebouwdobject2");
    }

    public static Gebruiksdoel getGebruiksdoelRandomSampleGenerator() {
        return new Gebruiksdoel().id(longCount.incrementAndGet()).gebruiksdoelgebouwdobject(UUID.randomUUID().toString());
    }
}
