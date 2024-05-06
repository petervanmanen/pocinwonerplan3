package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VthaanvraagofmeldingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vthaanvraagofmelding getVthaanvraagofmeldingSample1() {
        return new Vthaanvraagofmelding().id(1L).omschrijving("omschrijving1");
    }

    public static Vthaanvraagofmelding getVthaanvraagofmeldingSample2() {
        return new Vthaanvraagofmelding().id(2L).omschrijving("omschrijving2");
    }

    public static Vthaanvraagofmelding getVthaanvraagofmeldingRandomSampleGenerator() {
        return new Vthaanvraagofmelding().id(longCount.incrementAndGet()).omschrijving(UUID.randomUUID().toString());
    }
}
