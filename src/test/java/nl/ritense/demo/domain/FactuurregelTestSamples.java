package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FactuurregelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Factuurregel getFactuurregelSample1() {
        return new Factuurregel().id(1L).aantal("aantal1").btwpercentage("btwpercentage1").nummer("nummer1").omschrijving("omschrijving1");
    }

    public static Factuurregel getFactuurregelSample2() {
        return new Factuurregel().id(2L).aantal("aantal2").btwpercentage("btwpercentage2").nummer("nummer2").omschrijving("omschrijving2");
    }

    public static Factuurregel getFactuurregelRandomSampleGenerator() {
        return new Factuurregel()
            .id(longCount.incrementAndGet())
            .aantal(UUID.randomUUID().toString())
            .btwpercentage(UUID.randomUUID().toString())
            .nummer(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
