package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VastgoedcontractregelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vastgoedcontractregel getVastgoedcontractregelSample1() {
        return new Vastgoedcontractregel()
            .id(1L)
            .bedrag("bedrag1")
            .frequentie("frequentie1")
            .identificatie("identificatie1")
            .omschrijving("omschrijving1")
            .status("status1")
            .type("type1");
    }

    public static Vastgoedcontractregel getVastgoedcontractregelSample2() {
        return new Vastgoedcontractregel()
            .id(2L)
            .bedrag("bedrag2")
            .frequentie("frequentie2")
            .identificatie("identificatie2")
            .omschrijving("omschrijving2")
            .status("status2")
            .type("type2");
    }

    public static Vastgoedcontractregel getVastgoedcontractregelRandomSampleGenerator() {
        return new Vastgoedcontractregel()
            .id(longCount.incrementAndGet())
            .bedrag(UUID.randomUUID().toString())
            .frequentie(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
