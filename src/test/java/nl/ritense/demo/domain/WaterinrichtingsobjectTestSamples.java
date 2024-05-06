package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WaterinrichtingsobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Waterinrichtingsobject getWaterinrichtingsobjectSample1() {
        return new Waterinrichtingsobject()
            .id(1L)
            .aanleghoogte("aanleghoogte1")
            .breedte("breedte1")
            .jaarconserveren("jaarconserveren1")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd1")
            .kwaliteitsniveauactueel("kwaliteitsniveauactueel1")
            .kwaliteitsniveaugewenst("kwaliteitsniveaugewenst1")
            .lengte("lengte1")
            .leverancier("leverancier1")
            .materiaal("materiaal1")
            .oppervlakte("oppervlakte1");
    }

    public static Waterinrichtingsobject getWaterinrichtingsobjectSample2() {
        return new Waterinrichtingsobject()
            .id(2L)
            .aanleghoogte("aanleghoogte2")
            .breedte("breedte2")
            .jaarconserveren("jaarconserveren2")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd2")
            .kwaliteitsniveauactueel("kwaliteitsniveauactueel2")
            .kwaliteitsniveaugewenst("kwaliteitsniveaugewenst2")
            .lengte("lengte2")
            .leverancier("leverancier2")
            .materiaal("materiaal2")
            .oppervlakte("oppervlakte2");
    }

    public static Waterinrichtingsobject getWaterinrichtingsobjectRandomSampleGenerator() {
        return new Waterinrichtingsobject()
            .id(longCount.incrementAndGet())
            .aanleghoogte(UUID.randomUUID().toString())
            .breedte(UUID.randomUUID().toString())
            .jaarconserveren(UUID.randomUUID().toString())
            .jaaronderhouduitgevoerd(UUID.randomUUID().toString())
            .kwaliteitsniveauactueel(UUID.randomUUID().toString())
            .kwaliteitsniveaugewenst(UUID.randomUUID().toString())
            .lengte(UUID.randomUUID().toString())
            .leverancier(UUID.randomUUID().toString())
            .materiaal(UUID.randomUUID().toString())
            .oppervlakte(UUID.randomUUID().toString());
    }
}
