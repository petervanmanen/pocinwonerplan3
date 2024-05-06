package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class JuridischeregelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Juridischeregel getJuridischeregelSample1() {
        return new Juridischeregel().id(1L).omschrijving("omschrijving1").regeltekst("regeltekst1").thema("thema1");
    }

    public static Juridischeregel getJuridischeregelSample2() {
        return new Juridischeregel().id(2L).omschrijving("omschrijving2").regeltekst("regeltekst2").thema("thema2");
    }

    public static Juridischeregel getJuridischeregelRandomSampleGenerator() {
        return new Juridischeregel()
            .id(longCount.incrementAndGet())
            .omschrijving(UUID.randomUUID().toString())
            .regeltekst(UUID.randomUUID().toString())
            .thema(UUID.randomUUID().toString());
    }
}
