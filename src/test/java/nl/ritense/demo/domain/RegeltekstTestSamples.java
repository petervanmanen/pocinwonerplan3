package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RegeltekstTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Regeltekst getRegeltekstSample1() {
        return new Regeltekst().id(1L).identificatie("identificatie1").omschrijving("omschrijving1").tekst("tekst1");
    }

    public static Regeltekst getRegeltekstSample2() {
        return new Regeltekst().id(2L).identificatie("identificatie2").omschrijving("omschrijving2").tekst("tekst2");
    }

    public static Regeltekst getRegeltekstRandomSampleGenerator() {
        return new Regeltekst()
            .id(longCount.incrementAndGet())
            .identificatie(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .tekst(UUID.randomUUID().toString());
    }
}
