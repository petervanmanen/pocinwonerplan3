package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bak getBakSample1() {
        return new Bak()
            .id(1L)
            .breedte("breedte1")
            .diameter("diameter1")
            .gewichtleeg("gewichtleeg1")
            .gewichtvol("gewichtvol1")
            .hoogte("hoogte1")
            .inhoud("inhoud1")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd1")
            .kwaliteitsniveauactueel("kwaliteitsniveauactueel1")
            .kwaliteitsniveaugewenst("kwaliteitsniveaugewenst1")
            .lengte("lengte1")
            .materiaal("materiaal1")
            .vorm("vorm1");
    }

    public static Bak getBakSample2() {
        return new Bak()
            .id(2L)
            .breedte("breedte2")
            .diameter("diameter2")
            .gewichtleeg("gewichtleeg2")
            .gewichtvol("gewichtvol2")
            .hoogte("hoogte2")
            .inhoud("inhoud2")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd2")
            .kwaliteitsniveauactueel("kwaliteitsniveauactueel2")
            .kwaliteitsniveaugewenst("kwaliteitsniveaugewenst2")
            .lengte("lengte2")
            .materiaal("materiaal2")
            .vorm("vorm2");
    }

    public static Bak getBakRandomSampleGenerator() {
        return new Bak()
            .id(longCount.incrementAndGet())
            .breedte(UUID.randomUUID().toString())
            .diameter(UUID.randomUUID().toString())
            .gewichtleeg(UUID.randomUUID().toString())
            .gewichtvol(UUID.randomUUID().toString())
            .hoogte(UUID.randomUUID().toString())
            .inhoud(UUID.randomUUID().toString())
            .jaaronderhouduitgevoerd(UUID.randomUUID().toString())
            .kwaliteitsniveauactueel(UUID.randomUUID().toString())
            .kwaliteitsniveaugewenst(UUID.randomUUID().toString())
            .lengte(UUID.randomUUID().toString())
            .materiaal(UUID.randomUUID().toString())
            .vorm(UUID.randomUUID().toString());
    }
}
