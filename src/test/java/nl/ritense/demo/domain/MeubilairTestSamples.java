package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MeubilairTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Meubilair getMeubilairSample1() {
        return new Meubilair()
            .id(1L)
            .aanleghoogte("aanleghoogte1")
            .bouwjaar("bouwjaar1")
            .breedte("breedte1")
            .diameter("diameter1")
            .fabrikant("fabrikant1")
            .gewicht("gewicht1")
            .hoogte("hoogte1")
            .installateur("installateur1")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd1")
            .jaarpraktischeinde("jaarpraktischeinde1")
            .kleur("kleur1")
            .kwaliteitsniveauactueel("kwaliteitsniveauactueel1")
            .kwaliteitsniveaugewenst("kwaliteitsniveaugewenst1")
            .lengte("lengte1")
            .leverancier("leverancier1")
            .meubilairmateriaal("meubilairmateriaal1")
            .model("model1")
            .ondergrond("ondergrond1")
            .oppervlakte("oppervlakte1")
            .prijsaanschaf("prijsaanschaf1")
            .serienummer("serienummer1")
            .transponder("transponder1")
            .transponderlocatie("transponderlocatie1")
            .typefundering("typefundering1");
    }

    public static Meubilair getMeubilairSample2() {
        return new Meubilair()
            .id(2L)
            .aanleghoogte("aanleghoogte2")
            .bouwjaar("bouwjaar2")
            .breedte("breedte2")
            .diameter("diameter2")
            .fabrikant("fabrikant2")
            .gewicht("gewicht2")
            .hoogte("hoogte2")
            .installateur("installateur2")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd2")
            .jaarpraktischeinde("jaarpraktischeinde2")
            .kleur("kleur2")
            .kwaliteitsniveauactueel("kwaliteitsniveauactueel2")
            .kwaliteitsniveaugewenst("kwaliteitsniveaugewenst2")
            .lengte("lengte2")
            .leverancier("leverancier2")
            .meubilairmateriaal("meubilairmateriaal2")
            .model("model2")
            .ondergrond("ondergrond2")
            .oppervlakte("oppervlakte2")
            .prijsaanschaf("prijsaanschaf2")
            .serienummer("serienummer2")
            .transponder("transponder2")
            .transponderlocatie("transponderlocatie2")
            .typefundering("typefundering2");
    }

    public static Meubilair getMeubilairRandomSampleGenerator() {
        return new Meubilair()
            .id(longCount.incrementAndGet())
            .aanleghoogte(UUID.randomUUID().toString())
            .bouwjaar(UUID.randomUUID().toString())
            .breedte(UUID.randomUUID().toString())
            .diameter(UUID.randomUUID().toString())
            .fabrikant(UUID.randomUUID().toString())
            .gewicht(UUID.randomUUID().toString())
            .hoogte(UUID.randomUUID().toString())
            .installateur(UUID.randomUUID().toString())
            .jaaronderhouduitgevoerd(UUID.randomUUID().toString())
            .jaarpraktischeinde(UUID.randomUUID().toString())
            .kleur(UUID.randomUUID().toString())
            .kwaliteitsniveauactueel(UUID.randomUUID().toString())
            .kwaliteitsniveaugewenst(UUID.randomUUID().toString())
            .lengte(UUID.randomUUID().toString())
            .leverancier(UUID.randomUUID().toString())
            .meubilairmateriaal(UUID.randomUUID().toString())
            .model(UUID.randomUUID().toString())
            .ondergrond(UUID.randomUUID().toString())
            .oppervlakte(UUID.randomUUID().toString())
            .prijsaanschaf(UUID.randomUUID().toString())
            .serienummer(UUID.randomUUID().toString())
            .transponder(UUID.randomUUID().toString())
            .transponderlocatie(UUID.randomUUID().toString())
            .typefundering(UUID.randomUUID().toString());
    }
}
