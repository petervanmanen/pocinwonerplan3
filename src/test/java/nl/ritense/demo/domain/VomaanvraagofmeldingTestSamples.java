package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VomaanvraagofmeldingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vomaanvraagofmelding getVomaanvraagofmeldingSample1() {
        return new Vomaanvraagofmelding()
            .id(1L)
            .activiteiten("activiteiten1")
            .adres("adres1")
            .bagid("bagid1")
            .dossiernummer("dossiernummer1")
            .intaketype("intaketype1")
            .internnummer("internnummer1")
            .kadastraleaanduiding("kadastraleaanduiding1")
            .kenmerk("kenmerk1")
            .locatie("locatie1")
            .locatieomschrijving("locatieomschrijving1")
            .toelichting("toelichting1");
    }

    public static Vomaanvraagofmelding getVomaanvraagofmeldingSample2() {
        return new Vomaanvraagofmelding()
            .id(2L)
            .activiteiten("activiteiten2")
            .adres("adres2")
            .bagid("bagid2")
            .dossiernummer("dossiernummer2")
            .intaketype("intaketype2")
            .internnummer("internnummer2")
            .kadastraleaanduiding("kadastraleaanduiding2")
            .kenmerk("kenmerk2")
            .locatie("locatie2")
            .locatieomschrijving("locatieomschrijving2")
            .toelichting("toelichting2");
    }

    public static Vomaanvraagofmelding getVomaanvraagofmeldingRandomSampleGenerator() {
        return new Vomaanvraagofmelding()
            .id(longCount.incrementAndGet())
            .activiteiten(UUID.randomUUID().toString())
            .adres(UUID.randomUUID().toString())
            .bagid(UUID.randomUUID().toString())
            .dossiernummer(UUID.randomUUID().toString())
            .intaketype(UUID.randomUUID().toString())
            .internnummer(UUID.randomUUID().toString())
            .kadastraleaanduiding(UUID.randomUUID().toString())
            .kenmerk(UUID.randomUUID().toString())
            .locatie(UUID.randomUUID().toString())
            .locatieomschrijving(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString());
    }
}
