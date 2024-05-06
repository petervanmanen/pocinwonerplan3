package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MoraanvraagofmeldingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Moraanvraagofmelding getMoraanvraagofmeldingSample1() {
        return new Moraanvraagofmelding()
            .id(1L)
            .crow("crow1")
            .locatie("locatie1")
            .locatieomschrijving("locatieomschrijving1")
            .meldingomschrijving("meldingomschrijving1")
            .meldingtekst("meldingtekst1");
    }

    public static Moraanvraagofmelding getMoraanvraagofmeldingSample2() {
        return new Moraanvraagofmelding()
            .id(2L)
            .crow("crow2")
            .locatie("locatie2")
            .locatieomschrijving("locatieomschrijving2")
            .meldingomschrijving("meldingomschrijving2")
            .meldingtekst("meldingtekst2");
    }

    public static Moraanvraagofmelding getMoraanvraagofmeldingRandomSampleGenerator() {
        return new Moraanvraagofmelding()
            .id(longCount.incrementAndGet())
            .crow(UUID.randomUUID().toString())
            .locatie(UUID.randomUUID().toString())
            .locatieomschrijving(UUID.randomUUID().toString())
            .meldingomschrijving(UUID.randomUUID().toString())
            .meldingtekst(UUID.randomUUID().toString());
    }
}
