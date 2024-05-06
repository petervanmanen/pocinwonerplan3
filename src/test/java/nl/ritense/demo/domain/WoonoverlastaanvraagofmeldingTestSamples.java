package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WoonoverlastaanvraagofmeldingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Woonoverlastaanvraagofmelding getWoonoverlastaanvraagofmeldingSample1() {
        return new Woonoverlastaanvraagofmelding()
            .id(1L)
            .locatie("locatie1")
            .locatieomschrijving("locatieomschrijving1")
            .meldingomschrijving("meldingomschrijving1")
            .meldingtekst("meldingtekst1");
    }

    public static Woonoverlastaanvraagofmelding getWoonoverlastaanvraagofmeldingSample2() {
        return new Woonoverlastaanvraagofmelding()
            .id(2L)
            .locatie("locatie2")
            .locatieomschrijving("locatieomschrijving2")
            .meldingomschrijving("meldingomschrijving2")
            .meldingtekst("meldingtekst2");
    }

    public static Woonoverlastaanvraagofmelding getWoonoverlastaanvraagofmeldingRandomSampleGenerator() {
        return new Woonoverlastaanvraagofmelding()
            .id(longCount.incrementAndGet())
            .locatie(UUID.randomUUID().toString())
            .locatieomschrijving(UUID.randomUUID().toString())
            .meldingomschrijving(UUID.randomUUID().toString())
            .meldingtekst(UUID.randomUUID().toString());
    }
}
