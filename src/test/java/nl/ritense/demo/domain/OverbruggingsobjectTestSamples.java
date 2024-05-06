package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OverbruggingsobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Overbruggingsobject getOverbruggingsobjectSample1() {
        return new Overbruggingsobject()
            .id(1L)
            .aanleghoogte("aanleghoogte1")
            .bereikbaarheid("bereikbaarheid1")
            .breedte("breedte1")
            .hoogte("hoogte1")
            .installateur("installateur1")
            .jaarconserveren("jaarconserveren1")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd1")
            .jaarrenovatie("jaarrenovatie1")
            .jaarvervanging("jaarvervanging1")
            .kleur("kleur1")
            .kwaliteitsniveauactueel("kwaliteitsniveauactueel1")
            .kwaliteitsniveaugewenst("kwaliteitsniveaugewenst1")
            .lengte("lengte1")
            .minimumconditiescore("minimumconditiescore1")
            .onderhoudsregime("onderhoudsregime1")
            .oppervlakte("oppervlakte1")
            .overbruggingsobjectmateriaal("overbruggingsobjectmateriaal1")
            .overbruggingsobjectmodaliteit("overbruggingsobjectmodaliteit1")
            .technischelevensduur("technischelevensduur1")
            .typefundering("typefundering1")
            .vervangingswaarde("vervangingswaarde1");
    }

    public static Overbruggingsobject getOverbruggingsobjectSample2() {
        return new Overbruggingsobject()
            .id(2L)
            .aanleghoogte("aanleghoogte2")
            .bereikbaarheid("bereikbaarheid2")
            .breedte("breedte2")
            .hoogte("hoogte2")
            .installateur("installateur2")
            .jaarconserveren("jaarconserveren2")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd2")
            .jaarrenovatie("jaarrenovatie2")
            .jaarvervanging("jaarvervanging2")
            .kleur("kleur2")
            .kwaliteitsniveauactueel("kwaliteitsniveauactueel2")
            .kwaliteitsniveaugewenst("kwaliteitsniveaugewenst2")
            .lengte("lengte2")
            .minimumconditiescore("minimumconditiescore2")
            .onderhoudsregime("onderhoudsregime2")
            .oppervlakte("oppervlakte2")
            .overbruggingsobjectmateriaal("overbruggingsobjectmateriaal2")
            .overbruggingsobjectmodaliteit("overbruggingsobjectmodaliteit2")
            .technischelevensduur("technischelevensduur2")
            .typefundering("typefundering2")
            .vervangingswaarde("vervangingswaarde2");
    }

    public static Overbruggingsobject getOverbruggingsobjectRandomSampleGenerator() {
        return new Overbruggingsobject()
            .id(longCount.incrementAndGet())
            .aanleghoogte(UUID.randomUUID().toString())
            .bereikbaarheid(UUID.randomUUID().toString())
            .breedte(UUID.randomUUID().toString())
            .hoogte(UUID.randomUUID().toString())
            .installateur(UUID.randomUUID().toString())
            .jaarconserveren(UUID.randomUUID().toString())
            .jaaronderhouduitgevoerd(UUID.randomUUID().toString())
            .jaarrenovatie(UUID.randomUUID().toString())
            .jaarvervanging(UUID.randomUUID().toString())
            .kleur(UUID.randomUUID().toString())
            .kwaliteitsniveauactueel(UUID.randomUUID().toString())
            .kwaliteitsniveaugewenst(UUID.randomUUID().toString())
            .lengte(UUID.randomUUID().toString())
            .minimumconditiescore(UUID.randomUUID().toString())
            .onderhoudsregime(UUID.randomUUID().toString())
            .oppervlakte(UUID.randomUUID().toString())
            .overbruggingsobjectmateriaal(UUID.randomUUID().toString())
            .overbruggingsobjectmodaliteit(UUID.randomUUID().toString())
            .technischelevensduur(UUID.randomUUID().toString())
            .typefundering(UUID.randomUUID().toString())
            .vervangingswaarde(UUID.randomUUID().toString());
    }
}
