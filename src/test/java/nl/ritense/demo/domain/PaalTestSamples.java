package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PaalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Paal getPaalSample1() {
        return new Paal()
            .id(1L)
            .breedte("breedte1")
            .diameter("diameter1")
            .hoogte("hoogte1")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd1")
            .kwaliteitsniveauactueel("kwaliteitsniveauactueel1")
            .kwaliteitsniveaugewenst("kwaliteitsniveaugewenst1")
            .lengte("lengte1")
            .leverancier("leverancier1")
            .materiaal("materiaal1")
            .vorm("vorm1");
    }

    public static Paal getPaalSample2() {
        return new Paal()
            .id(2L)
            .breedte("breedte2")
            .diameter("diameter2")
            .hoogte("hoogte2")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd2")
            .kwaliteitsniveauactueel("kwaliteitsniveauactueel2")
            .kwaliteitsniveaugewenst("kwaliteitsniveaugewenst2")
            .lengte("lengte2")
            .leverancier("leverancier2")
            .materiaal("materiaal2")
            .vorm("vorm2");
    }

    public static Paal getPaalRandomSampleGenerator() {
        return new Paal()
            .id(longCount.incrementAndGet())
            .breedte(UUID.randomUUID().toString())
            .diameter(UUID.randomUUID().toString())
            .hoogte(UUID.randomUUID().toString())
            .jaaronderhouduitgevoerd(UUID.randomUUID().toString())
            .kwaliteitsniveauactueel(UUID.randomUUID().toString())
            .kwaliteitsniveaugewenst(UUID.randomUUID().toString())
            .lengte(UUID.randomUUID().toString())
            .leverancier(UUID.randomUUID().toString())
            .materiaal(UUID.randomUUID().toString())
            .vorm(UUID.randomUUID().toString());
    }
}
