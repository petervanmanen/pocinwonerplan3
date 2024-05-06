package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GeweldsincidentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Geweldsincident getGeweldsincidentSample1() {
        return new Geweldsincident().id(1L).omschrijving("omschrijving1").type("type1");
    }

    public static Geweldsincident getGeweldsincidentSample2() {
        return new Geweldsincident().id(2L).omschrijving("omschrijving2").type("type2");
    }

    public static Geweldsincident getGeweldsincidentRandomSampleGenerator() {
        return new Geweldsincident()
            .id(longCount.incrementAndGet())
            .omschrijving(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
