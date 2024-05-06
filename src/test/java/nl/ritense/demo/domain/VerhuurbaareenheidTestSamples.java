package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerhuurbaareenheidTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verhuurbaareenheid getVerhuurbaareenheidSample1() {
        return new Verhuurbaareenheid()
            .id(1L)
            .adres("adres1")
            .afmeting("afmeting1")
            .bezetting("bezetting1")
            .huurprijs("huurprijs1")
            .identificatie("identificatie1")
            .naam("naam1")
            .nettoomtrek("nettoomtrek1")
            .nettooppervlak("nettooppervlak1")
            .opmerkingen("opmerkingen1")
            .type("type1");
    }

    public static Verhuurbaareenheid getVerhuurbaareenheidSample2() {
        return new Verhuurbaareenheid()
            .id(2L)
            .adres("adres2")
            .afmeting("afmeting2")
            .bezetting("bezetting2")
            .huurprijs("huurprijs2")
            .identificatie("identificatie2")
            .naam("naam2")
            .nettoomtrek("nettoomtrek2")
            .nettooppervlak("nettooppervlak2")
            .opmerkingen("opmerkingen2")
            .type("type2");
    }

    public static Verhuurbaareenheid getVerhuurbaareenheidRandomSampleGenerator() {
        return new Verhuurbaareenheid()
            .id(longCount.incrementAndGet())
            .adres(UUID.randomUUID().toString())
            .afmeting(UUID.randomUUID().toString())
            .bezetting(UUID.randomUUID().toString())
            .huurprijs(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .nettoomtrek(UUID.randomUUID().toString())
            .nettooppervlak(UUID.randomUUID().toString())
            .opmerkingen(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
