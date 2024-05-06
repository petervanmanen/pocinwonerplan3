package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BeoordelingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Beoordeling getBeoordelingSample1() {
        return new Beoordeling().id(1L).omschrijving("omschrijving1").oordeel("oordeel1");
    }

    public static Beoordeling getBeoordelingSample2() {
        return new Beoordeling().id(2L).omschrijving("omschrijving2").oordeel("oordeel2");
    }

    public static Beoordeling getBeoordelingRandomSampleGenerator() {
        return new Beoordeling()
            .id(longCount.incrementAndGet())
            .omschrijving(UUID.randomUUID().toString())
            .oordeel(UUID.randomUUID().toString());
    }
}
