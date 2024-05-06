package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ZaaktypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Zaaktype getZaaktypeSample1() {
        return new Zaaktype()
            .id(1L)
            .archiefcode("archiefcode1")
            .datumbegingeldigheidzaaktype("datumbegingeldigheidzaaktype1")
            .datumeindegeldigheidzaaktype("datumeindegeldigheidzaaktype1")
            .doorlooptijdbehandeling("doorlooptijdbehandeling1")
            .indicatiepublicatie("indicatiepublicatie1")
            .publicatietekst("publicatietekst1")
            .servicenormbehandeling("servicenormbehandeling1")
            .trefwoord("trefwoord1")
            .vertrouwelijkaanduiding("vertrouwelijkaanduiding1")
            .zaakcategorie("zaakcategorie1")
            .zaaktypeomschrijving("zaaktypeomschrijving1")
            .zaaktypeomschrijvinggeneriek("zaaktypeomschrijvinggeneriek1");
    }

    public static Zaaktype getZaaktypeSample2() {
        return new Zaaktype()
            .id(2L)
            .archiefcode("archiefcode2")
            .datumbegingeldigheidzaaktype("datumbegingeldigheidzaaktype2")
            .datumeindegeldigheidzaaktype("datumeindegeldigheidzaaktype2")
            .doorlooptijdbehandeling("doorlooptijdbehandeling2")
            .indicatiepublicatie("indicatiepublicatie2")
            .publicatietekst("publicatietekst2")
            .servicenormbehandeling("servicenormbehandeling2")
            .trefwoord("trefwoord2")
            .vertrouwelijkaanduiding("vertrouwelijkaanduiding2")
            .zaakcategorie("zaakcategorie2")
            .zaaktypeomschrijving("zaaktypeomschrijving2")
            .zaaktypeomschrijvinggeneriek("zaaktypeomschrijvinggeneriek2");
    }

    public static Zaaktype getZaaktypeRandomSampleGenerator() {
        return new Zaaktype()
            .id(longCount.incrementAndGet())
            .archiefcode(UUID.randomUUID().toString())
            .datumbegingeldigheidzaaktype(UUID.randomUUID().toString())
            .datumeindegeldigheidzaaktype(UUID.randomUUID().toString())
            .doorlooptijdbehandeling(UUID.randomUUID().toString())
            .indicatiepublicatie(UUID.randomUUID().toString())
            .publicatietekst(UUID.randomUUID().toString())
            .servicenormbehandeling(UUID.randomUUID().toString())
            .trefwoord(UUID.randomUUID().toString())
            .vertrouwelijkaanduiding(UUID.randomUUID().toString())
            .zaakcategorie(UUID.randomUUID().toString())
            .zaaktypeomschrijving(UUID.randomUUID().toString())
            .zaaktypeomschrijvinggeneriek(UUID.randomUUID().toString());
    }
}
