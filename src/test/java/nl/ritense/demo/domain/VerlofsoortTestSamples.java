package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerlofsoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verlofsoort getVerlofsoortSample1() {
        return new Verlofsoort().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Verlofsoort getVerlofsoortSample2() {
        return new Verlofsoort().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Verlofsoort getVerlofsoortRandomSampleGenerator() {
        return new Verlofsoort()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
