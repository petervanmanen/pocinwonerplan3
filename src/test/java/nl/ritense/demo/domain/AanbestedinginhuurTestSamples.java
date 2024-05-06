package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AanbestedinginhuurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aanbestedinginhuur getAanbestedinginhuurSample1() {
        return new Aanbestedinginhuur()
            .id(1L)
            .aanvraaggesloten("aanvraaggesloten1")
            .aanvraagnummer("aanvraagnummer1")
            .datumcreatie("datumcreatie1")
            .datumopeningkluis("datumopeningkluis1")
            .datumsluiting("datumsluiting1")
            .datumverzending("datumverzending1")
            .fase("fase1")
            .hoogstetarief("hoogstetarief1")
            .laagstetarief("laagstetarief1")
            .omschrijving("omschrijving1")
            .perceel("perceel1")
            .procedure("procedure1")
            .projectnaam("projectnaam1")
            .projectreferentie("projectreferentie1")
            .publicatie("publicatie1")
            .referentie("referentie1")
            .status("status1")
            .titel("titel1")
            .type("type1");
    }

    public static Aanbestedinginhuur getAanbestedinginhuurSample2() {
        return new Aanbestedinginhuur()
            .id(2L)
            .aanvraaggesloten("aanvraaggesloten2")
            .aanvraagnummer("aanvraagnummer2")
            .datumcreatie("datumcreatie2")
            .datumopeningkluis("datumopeningkluis2")
            .datumsluiting("datumsluiting2")
            .datumverzending("datumverzending2")
            .fase("fase2")
            .hoogstetarief("hoogstetarief2")
            .laagstetarief("laagstetarief2")
            .omschrijving("omschrijving2")
            .perceel("perceel2")
            .procedure("procedure2")
            .projectnaam("projectnaam2")
            .projectreferentie("projectreferentie2")
            .publicatie("publicatie2")
            .referentie("referentie2")
            .status("status2")
            .titel("titel2")
            .type("type2");
    }

    public static Aanbestedinginhuur getAanbestedinginhuurRandomSampleGenerator() {
        return new Aanbestedinginhuur()
            .id(longCount.incrementAndGet())
            .aanvraaggesloten(UUID.randomUUID().toString())
            .aanvraagnummer(UUID.randomUUID().toString())
            .datumcreatie(UUID.randomUUID().toString())
            .datumopeningkluis(UUID.randomUUID().toString())
            .datumsluiting(UUID.randomUUID().toString())
            .datumverzending(UUID.randomUUID().toString())
            .fase(UUID.randomUUID().toString())
            .hoogstetarief(UUID.randomUUID().toString())
            .laagstetarief(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .perceel(UUID.randomUUID().toString())
            .procedure(UUID.randomUUID().toString())
            .projectnaam(UUID.randomUUID().toString())
            .projectreferentie(UUID.randomUUID().toString())
            .publicatie(UUID.randomUUID().toString())
            .referentie(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .titel(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
