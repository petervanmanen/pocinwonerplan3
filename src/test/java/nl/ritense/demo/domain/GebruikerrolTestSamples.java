package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GebruikerrolTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Gebruikerrol getGebruikerrolSample1() {
        return new Gebruikerrol().id(1L).rol("rol1");
    }

    public static Gebruikerrol getGebruikerrolSample2() {
        return new Gebruikerrol().id(2L).rol("rol2");
    }

    public static Gebruikerrol getGebruikerrolRandomSampleGenerator() {
        return new Gebruikerrol().id(longCount.incrementAndGet()).rol(UUID.randomUUID().toString());
    }
}
