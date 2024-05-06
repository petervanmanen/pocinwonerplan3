package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BouwwerkTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bouwwerk getBouwwerkSample1() {
        return new Bouwwerk()
            .id(1L)
            .aanleghoogte("aanleghoogte1")
            .bouwwerkmateriaal("bouwwerkmateriaal1")
            .breedte("breedte1")
            .fabrikant("fabrikant1")
            .hoogte("hoogte1")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd1")
            .lengte("lengte1")
            .leverancier("leverancier1")
            .oppervlakte("oppervlakte1")
            .typefundering("typefundering1");
    }

    public static Bouwwerk getBouwwerkSample2() {
        return new Bouwwerk()
            .id(2L)
            .aanleghoogte("aanleghoogte2")
            .bouwwerkmateriaal("bouwwerkmateriaal2")
            .breedte("breedte2")
            .fabrikant("fabrikant2")
            .hoogte("hoogte2")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd2")
            .lengte("lengte2")
            .leverancier("leverancier2")
            .oppervlakte("oppervlakte2")
            .typefundering("typefundering2");
    }

    public static Bouwwerk getBouwwerkRandomSampleGenerator() {
        return new Bouwwerk()
            .id(longCount.incrementAndGet())
            .aanleghoogte(UUID.randomUUID().toString())
            .bouwwerkmateriaal(UUID.randomUUID().toString())
            .breedte(UUID.randomUUID().toString())
            .fabrikant(UUID.randomUUID().toString())
            .hoogte(UUID.randomUUID().toString())
            .jaaronderhouduitgevoerd(UUID.randomUUID().toString())
            .lengte(UUID.randomUUID().toString())
            .leverancier(UUID.randomUUID().toString())
            .oppervlakte(UUID.randomUUID().toString())
            .typefundering(UUID.randomUUID().toString());
    }
}
