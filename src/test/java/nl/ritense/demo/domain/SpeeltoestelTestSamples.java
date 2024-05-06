package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SpeeltoestelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Speeltoestel getSpeeltoestelSample1() {
        return new Speeltoestel()
            .id(1L)
            .catalogusprijs("catalogusprijs1")
            .certificaatnummer("certificaatnummer1")
            .certificeringsinstantie("certificeringsinstantie1")
            .controlefrequentie("controlefrequentie1")
            .inspectievolgorde("inspectievolgorde1")
            .installatiekosten("installatiekosten1")
            .speelterrein("speelterrein1")
            .speeltoesteltoestelonderdeel("speeltoesteltoestelonderdeel1")
            .technischelevensduur("technischelevensduur1")
            .toestelcode("toestelcode1")
            .toestelgroep("toestelgroep1")
            .toestelnaam("toestelnaam1")
            .type("type1")
            .typenummer("typenummer1")
            .typeplus("typeplus1")
            .typeplus2("typeplus21")
            .valruimtehoogte("valruimtehoogte1")
            .valruimteomvang("valruimteomvang1")
            .vrijevalhoogte("vrijevalhoogte1");
    }

    public static Speeltoestel getSpeeltoestelSample2() {
        return new Speeltoestel()
            .id(2L)
            .catalogusprijs("catalogusprijs2")
            .certificaatnummer("certificaatnummer2")
            .certificeringsinstantie("certificeringsinstantie2")
            .controlefrequentie("controlefrequentie2")
            .inspectievolgorde("inspectievolgorde2")
            .installatiekosten("installatiekosten2")
            .speelterrein("speelterrein2")
            .speeltoesteltoestelonderdeel("speeltoesteltoestelonderdeel2")
            .technischelevensduur("technischelevensduur2")
            .toestelcode("toestelcode2")
            .toestelgroep("toestelgroep2")
            .toestelnaam("toestelnaam2")
            .type("type2")
            .typenummer("typenummer2")
            .typeplus("typeplus2")
            .typeplus2("typeplus22")
            .valruimtehoogte("valruimtehoogte2")
            .valruimteomvang("valruimteomvang2")
            .vrijevalhoogte("vrijevalhoogte2");
    }

    public static Speeltoestel getSpeeltoestelRandomSampleGenerator() {
        return new Speeltoestel()
            .id(longCount.incrementAndGet())
            .catalogusprijs(UUID.randomUUID().toString())
            .certificaatnummer(UUID.randomUUID().toString())
            .certificeringsinstantie(UUID.randomUUID().toString())
            .controlefrequentie(UUID.randomUUID().toString())
            .inspectievolgorde(UUID.randomUUID().toString())
            .installatiekosten(UUID.randomUUID().toString())
            .speelterrein(UUID.randomUUID().toString())
            .speeltoesteltoestelonderdeel(UUID.randomUUID().toString())
            .technischelevensduur(UUID.randomUUID().toString())
            .toestelcode(UUID.randomUUID().toString())
            .toestelgroep(UUID.randomUUID().toString())
            .toestelnaam(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .typenummer(UUID.randomUUID().toString())
            .typeplus(UUID.randomUUID().toString())
            .typeplus2(UUID.randomUUID().toString())
            .valruimtehoogte(UUID.randomUUID().toString())
            .valruimteomvang(UUID.randomUUID().toString())
            .vrijevalhoogte(UUID.randomUUID().toString());
    }
}
