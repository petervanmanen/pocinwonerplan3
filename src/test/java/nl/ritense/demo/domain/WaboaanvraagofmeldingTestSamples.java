package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WaboaanvraagofmeldingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Waboaanvraagofmelding getWaboaanvraagofmeldingSample1() {
        return new Waboaanvraagofmelding()
            .id(1L)
            .olonummer("olonummer1")
            .omschrijving("omschrijving1")
            .registratienummer("registratienummer1");
    }

    public static Waboaanvraagofmelding getWaboaanvraagofmeldingSample2() {
        return new Waboaanvraagofmelding()
            .id(2L)
            .olonummer("olonummer2")
            .omschrijving("omschrijving2")
            .registratienummer("registratienummer2");
    }

    public static Waboaanvraagofmelding getWaboaanvraagofmeldingRandomSampleGenerator() {
        return new Waboaanvraagofmelding()
            .id(longCount.incrementAndGet())
            .olonummer(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .registratienummer(UUID.randomUUID().toString());
    }
}
