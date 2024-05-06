package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BesluittypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Besluittype getBesluittypeSample1() {
        return new Besluittype()
            .id(1L)
            .besluitcategorie("besluitcategorie1")
            .besluittypeomschrijving("besluittypeomschrijving1")
            .besluittypeomschrijvinggeneriek("besluittypeomschrijvinggeneriek1")
            .datumbegingeldigheidbesluittype("datumbegingeldigheidbesluittype1")
            .datumeindegeldigheidbesluittype("datumeindegeldigheidbesluittype1")
            .indicatiepublicatie("indicatiepublicatie1")
            .publicatietekst("publicatietekst1")
            .publicatietermijn("publicatietermijn1")
            .reactietermijn("reactietermijn1");
    }

    public static Besluittype getBesluittypeSample2() {
        return new Besluittype()
            .id(2L)
            .besluitcategorie("besluitcategorie2")
            .besluittypeomschrijving("besluittypeomschrijving2")
            .besluittypeomschrijvinggeneriek("besluittypeomschrijvinggeneriek2")
            .datumbegingeldigheidbesluittype("datumbegingeldigheidbesluittype2")
            .datumeindegeldigheidbesluittype("datumeindegeldigheidbesluittype2")
            .indicatiepublicatie("indicatiepublicatie2")
            .publicatietekst("publicatietekst2")
            .publicatietermijn("publicatietermijn2")
            .reactietermijn("reactietermijn2");
    }

    public static Besluittype getBesluittypeRandomSampleGenerator() {
        return new Besluittype()
            .id(longCount.incrementAndGet())
            .besluitcategorie(UUID.randomUUID().toString())
            .besluittypeomschrijving(UUID.randomUUID().toString())
            .besluittypeomschrijvinggeneriek(UUID.randomUUID().toString())
            .datumbegingeldigheidbesluittype(UUID.randomUUID().toString())
            .datumeindegeldigheidbesluittype(UUID.randomUUID().toString())
            .indicatiepublicatie(UUID.randomUUID().toString())
            .publicatietekst(UUID.randomUUID().toString())
            .publicatietermijn(UUID.randomUUID().toString())
            .reactietermijn(UUID.randomUUID().toString());
    }
}
