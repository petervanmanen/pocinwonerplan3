package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WoonfraudeaanvraagofmeldingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Woonfraudeaanvraagofmelding getWoonfraudeaanvraagofmeldingSample1() {
        return new Woonfraudeaanvraagofmelding()
            .id(1L)
            .adres("adres1")
            .categorie("categorie1")
            .locatieomschrijving("locatieomschrijving1")
            .meldingomschrijving("meldingomschrijving1")
            .meldingtekst("meldingtekst1");
    }

    public static Woonfraudeaanvraagofmelding getWoonfraudeaanvraagofmeldingSample2() {
        return new Woonfraudeaanvraagofmelding()
            .id(2L)
            .adres("adres2")
            .categorie("categorie2")
            .locatieomschrijving("locatieomschrijving2")
            .meldingomschrijving("meldingomschrijving2")
            .meldingtekst("meldingtekst2");
    }

    public static Woonfraudeaanvraagofmelding getWoonfraudeaanvraagofmeldingRandomSampleGenerator() {
        return new Woonfraudeaanvraagofmelding()
            .id(longCount.incrementAndGet())
            .adres(UUID.randomUUID().toString())
            .categorie(UUID.randomUUID().toString())
            .locatieomschrijving(UUID.randomUUID().toString())
            .meldingomschrijving(UUID.randomUUID().toString())
            .meldingtekst(UUID.randomUUID().toString());
    }
}
