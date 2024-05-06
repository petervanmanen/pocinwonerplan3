package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NatuurlijkpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Natuurlijkpersoon getNatuurlijkpersoonSample1() {
        return new Natuurlijkpersoon()
            .id(1L)
            .empty("empty1")
            .aanduidingbijzondernederlanderschappersoon("aanduidingbijzondernederlanderschappersoon1")
            .aanduidingnaamgebruik("aanduidingnaamgebruik1")
            .aanhefaanschrijving("aanhefaanschrijving1")
            .academischetitel("academischetitel1")
            .achternaam("achternaam1")
            .adellijketitelofpredikaat("adellijketitelofpredikaat1")
            .anummer("anummer1")
            .burgerservicenummer("burgerservicenummer1")
            .datumgeboorte("datumgeboorte1")
            .datumoverlijden("datumoverlijden1")
            .geboorteland("geboorteland1")
            .geboorteplaats("geboorteplaats1")
            .geslachtsaanduiding("geslachtsaanduiding1")
            .geslachtsnaam("geslachtsnaam1")
            .geslachtsnaamaanschrijving("geslachtsnaamaanschrijving1")
            .handlichting("handlichting1")
            .landoverlijden("landoverlijden1")
            .nationaliteit("nationaliteit1")
            .overlijdensplaats("overlijdensplaats1")
            .voorlettersaanschrijving("voorlettersaanschrijving1")
            .voornamen("voornamen1")
            .voornamenaanschrijving("voornamenaanschrijving1")
            .voorvoegselgeslachtsnaam("voorvoegselgeslachtsnaam1");
    }

    public static Natuurlijkpersoon getNatuurlijkpersoonSample2() {
        return new Natuurlijkpersoon()
            .id(2L)
            .empty("empty2")
            .aanduidingbijzondernederlanderschappersoon("aanduidingbijzondernederlanderschappersoon2")
            .aanduidingnaamgebruik("aanduidingnaamgebruik2")
            .aanhefaanschrijving("aanhefaanschrijving2")
            .academischetitel("academischetitel2")
            .achternaam("achternaam2")
            .adellijketitelofpredikaat("adellijketitelofpredikaat2")
            .anummer("anummer2")
            .burgerservicenummer("burgerservicenummer2")
            .datumgeboorte("datumgeboorte2")
            .datumoverlijden("datumoverlijden2")
            .geboorteland("geboorteland2")
            .geboorteplaats("geboorteplaats2")
            .geslachtsaanduiding("geslachtsaanduiding2")
            .geslachtsnaam("geslachtsnaam2")
            .geslachtsnaamaanschrijving("geslachtsnaamaanschrijving2")
            .handlichting("handlichting2")
            .landoverlijden("landoverlijden2")
            .nationaliteit("nationaliteit2")
            .overlijdensplaats("overlijdensplaats2")
            .voorlettersaanschrijving("voorlettersaanschrijving2")
            .voornamen("voornamen2")
            .voornamenaanschrijving("voornamenaanschrijving2")
            .voorvoegselgeslachtsnaam("voorvoegselgeslachtsnaam2");
    }

    public static Natuurlijkpersoon getNatuurlijkpersoonRandomSampleGenerator() {
        return new Natuurlijkpersoon()
            .id(longCount.incrementAndGet())
            .empty(UUID.randomUUID().toString())
            .aanduidingbijzondernederlanderschappersoon(UUID.randomUUID().toString())
            .aanduidingnaamgebruik(UUID.randomUUID().toString())
            .aanhefaanschrijving(UUID.randomUUID().toString())
            .academischetitel(UUID.randomUUID().toString())
            .achternaam(UUID.randomUUID().toString())
            .adellijketitelofpredikaat(UUID.randomUUID().toString())
            .anummer(UUID.randomUUID().toString())
            .burgerservicenummer(UUID.randomUUID().toString())
            .datumgeboorte(UUID.randomUUID().toString())
            .datumoverlijden(UUID.randomUUID().toString())
            .geboorteland(UUID.randomUUID().toString())
            .geboorteplaats(UUID.randomUUID().toString())
            .geslachtsaanduiding(UUID.randomUUID().toString())
            .geslachtsnaam(UUID.randomUUID().toString())
            .geslachtsnaamaanschrijving(UUID.randomUUID().toString())
            .handlichting(UUID.randomUUID().toString())
            .landoverlijden(UUID.randomUUID().toString())
            .nationaliteit(UUID.randomUUID().toString())
            .overlijdensplaats(UUID.randomUUID().toString())
            .voorlettersaanschrijving(UUID.randomUUID().toString())
            .voornamen(UUID.randomUUID().toString())
            .voornamenaanschrijving(UUID.randomUUID().toString())
            .voorvoegselgeslachtsnaam(UUID.randomUUID().toString());
    }
}
