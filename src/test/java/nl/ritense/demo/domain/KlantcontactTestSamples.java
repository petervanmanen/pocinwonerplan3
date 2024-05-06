package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KlantcontactTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Klantcontact getKlantcontactSample1() {
        return new Klantcontact()
            .id(1L)
            .eindtijd("eindtijd1")
            .kanaal("kanaal1")
            .notitie("notitie1")
            .starttijd("starttijd1")
            .tijdsduur("tijdsduur1")
            .toelichting("toelichting1")
            .wachttijdtotaal("wachttijdtotaal1");
    }

    public static Klantcontact getKlantcontactSample2() {
        return new Klantcontact()
            .id(2L)
            .eindtijd("eindtijd2")
            .kanaal("kanaal2")
            .notitie("notitie2")
            .starttijd("starttijd2")
            .tijdsduur("tijdsduur2")
            .toelichting("toelichting2")
            .wachttijdtotaal("wachttijdtotaal2");
    }

    public static Klantcontact getKlantcontactRandomSampleGenerator() {
        return new Klantcontact()
            .id(longCount.incrementAndGet())
            .eindtijd(UUID.randomUUID().toString())
            .kanaal(UUID.randomUUID().toString())
            .notitie(UUID.randomUUID().toString())
            .starttijd(UUID.randomUUID().toString())
            .tijdsduur(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString())
            .wachttijdtotaal(UUID.randomUUID().toString());
    }
}
