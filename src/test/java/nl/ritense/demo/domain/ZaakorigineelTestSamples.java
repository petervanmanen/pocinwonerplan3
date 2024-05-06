package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ZaakorigineelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Zaakorigineel getZaakorigineelSample1() {
        return new Zaakorigineel()
            .id(1L)
            .anderzaakobject("anderzaakobject1")
            .archiefnominatie("archiefnominatie1")
            .datumeinde("datumeinde1")
            .datumeindegepland("datumeindegepland1")
            .datumeindeuiterlijkeafdoening("datumeindeuiterlijkeafdoening1")
            .datumlaatstebetaling("datumlaatstebetaling1")
            .datumpublicatie("datumpublicatie1")
            .datumregistratie("datumregistratie1")
            .datumstart("datumstart1")
            .datumvernietigingdossier("datumvernietigingdossier1")
            .indicatiebetaling("indicatiebetaling1")
            .indicatiedeelzaken("indicatiedeelzaken1")
            .kenmerk("kenmerk1")
            .omschrijving("omschrijving1")
            .omschrijvingresultaat("omschrijvingresultaat1")
            .opschorting("opschorting1")
            .toelichting("toelichting1")
            .toelichtingresultaat("toelichtingresultaat1")
            .verlenging("verlenging1")
            .zaakidentificatie("zaakidentificatie1")
            .zaakniveau("zaakniveau1");
    }

    public static Zaakorigineel getZaakorigineelSample2() {
        return new Zaakorigineel()
            .id(2L)
            .anderzaakobject("anderzaakobject2")
            .archiefnominatie("archiefnominatie2")
            .datumeinde("datumeinde2")
            .datumeindegepland("datumeindegepland2")
            .datumeindeuiterlijkeafdoening("datumeindeuiterlijkeafdoening2")
            .datumlaatstebetaling("datumlaatstebetaling2")
            .datumpublicatie("datumpublicatie2")
            .datumregistratie("datumregistratie2")
            .datumstart("datumstart2")
            .datumvernietigingdossier("datumvernietigingdossier2")
            .indicatiebetaling("indicatiebetaling2")
            .indicatiedeelzaken("indicatiedeelzaken2")
            .kenmerk("kenmerk2")
            .omschrijving("omschrijving2")
            .omschrijvingresultaat("omschrijvingresultaat2")
            .opschorting("opschorting2")
            .toelichting("toelichting2")
            .toelichtingresultaat("toelichtingresultaat2")
            .verlenging("verlenging2")
            .zaakidentificatie("zaakidentificatie2")
            .zaakniveau("zaakniveau2");
    }

    public static Zaakorigineel getZaakorigineelRandomSampleGenerator() {
        return new Zaakorigineel()
            .id(longCount.incrementAndGet())
            .anderzaakobject(UUID.randomUUID().toString())
            .archiefnominatie(UUID.randomUUID().toString())
            .datumeinde(UUID.randomUUID().toString())
            .datumeindegepland(UUID.randomUUID().toString())
            .datumeindeuiterlijkeafdoening(UUID.randomUUID().toString())
            .datumlaatstebetaling(UUID.randomUUID().toString())
            .datumpublicatie(UUID.randomUUID().toString())
            .datumregistratie(UUID.randomUUID().toString())
            .datumstart(UUID.randomUUID().toString())
            .datumvernietigingdossier(UUID.randomUUID().toString())
            .indicatiebetaling(UUID.randomUUID().toString())
            .indicatiedeelzaken(UUID.randomUUID().toString())
            .kenmerk(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .omschrijvingresultaat(UUID.randomUUID().toString())
            .opschorting(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString())
            .toelichtingresultaat(UUID.randomUUID().toString())
            .verlenging(UUID.randomUUID().toString())
            .zaakidentificatie(UUID.randomUUID().toString())
            .zaakniveau(UUID.randomUUID().toString());
    }
}
