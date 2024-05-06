package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ZaakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Zaak getZaakSample1() {
        return new Zaak()
            .id(1L)
            .empty("empty1")
            .archiefnominatie("archiefnominatie1")
            .datumeinde("datumeinde1")
            .datumeindegepland("datumeindegepland1")
            .datumeindeuiterlijkeafdoening("datumeindeuiterlijkeafdoening1")
            .datumlaatstebetaling("datumlaatstebetaling1")
            .datumpublicatie("datumpublicatie1")
            .datumregistratie("datumregistratie1")
            .datumstart("datumstart1")
            .datumvernietigingdossier("datumvernietigingdossier1")
            .document("document1")
            .duurverlenging("duurverlenging1")
            .indicatiebetaling("indicatiebetaling1")
            .indicatiedeelzaken("indicatiedeelzaken1")
            .indicatieopschorting("indicatieopschorting1")
            .leges("leges1")
            .omschrijving("omschrijving1")
            .omschrijvingresultaat("omschrijvingresultaat1")
            .redenopschorting("redenopschorting1")
            .redenverlenging("redenverlenging1")
            .toelichting("toelichting1")
            .toelichtingresultaat("toelichtingresultaat1")
            .vertrouwelijkheid("vertrouwelijkheid1")
            .zaakidentificatie("zaakidentificatie1")
            .zaakniveau("zaakniveau1");
    }

    public static Zaak getZaakSample2() {
        return new Zaak()
            .id(2L)
            .empty("empty2")
            .archiefnominatie("archiefnominatie2")
            .datumeinde("datumeinde2")
            .datumeindegepland("datumeindegepland2")
            .datumeindeuiterlijkeafdoening("datumeindeuiterlijkeafdoening2")
            .datumlaatstebetaling("datumlaatstebetaling2")
            .datumpublicatie("datumpublicatie2")
            .datumregistratie("datumregistratie2")
            .datumstart("datumstart2")
            .datumvernietigingdossier("datumvernietigingdossier2")
            .document("document2")
            .duurverlenging("duurverlenging2")
            .indicatiebetaling("indicatiebetaling2")
            .indicatiedeelzaken("indicatiedeelzaken2")
            .indicatieopschorting("indicatieopschorting2")
            .leges("leges2")
            .omschrijving("omschrijving2")
            .omschrijvingresultaat("omschrijvingresultaat2")
            .redenopschorting("redenopschorting2")
            .redenverlenging("redenverlenging2")
            .toelichting("toelichting2")
            .toelichtingresultaat("toelichtingresultaat2")
            .vertrouwelijkheid("vertrouwelijkheid2")
            .zaakidentificatie("zaakidentificatie2")
            .zaakniveau("zaakniveau2");
    }

    public static Zaak getZaakRandomSampleGenerator() {
        return new Zaak()
            .id(longCount.incrementAndGet())
            .empty(UUID.randomUUID().toString())
            .archiefnominatie(UUID.randomUUID().toString())
            .datumeinde(UUID.randomUUID().toString())
            .datumeindegepland(UUID.randomUUID().toString())
            .datumeindeuiterlijkeafdoening(UUID.randomUUID().toString())
            .datumlaatstebetaling(UUID.randomUUID().toString())
            .datumpublicatie(UUID.randomUUID().toString())
            .datumregistratie(UUID.randomUUID().toString())
            .datumstart(UUID.randomUUID().toString())
            .datumvernietigingdossier(UUID.randomUUID().toString())
            .document(UUID.randomUUID().toString())
            .duurverlenging(UUID.randomUUID().toString())
            .indicatiebetaling(UUID.randomUUID().toString())
            .indicatiedeelzaken(UUID.randomUUID().toString())
            .indicatieopschorting(UUID.randomUUID().toString())
            .leges(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .omschrijvingresultaat(UUID.randomUUID().toString())
            .redenopschorting(UUID.randomUUID().toString())
            .redenverlenging(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString())
            .toelichtingresultaat(UUID.randomUUID().toString())
            .vertrouwelijkheid(UUID.randomUUID().toString())
            .zaakidentificatie(UUID.randomUUID().toString())
            .zaakniveau(UUID.randomUUID().toString());
    }
}
