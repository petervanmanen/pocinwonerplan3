package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ScheidingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Scheiding getScheidingSample1() {
        return new Scheiding()
            .id(1L)
            .aanleghoogte("aanleghoogte1")
            .breedte("breedte1")
            .hoogte("hoogte1")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd1")
            .lengte("lengte1")
            .leverancier("leverancier1")
            .eobjectnaam("eobjectnaam1")
            .eobjectnummer("eobjectnummer1")
            .oppervlakte("oppervlakte1")
            .scheidingmateriaal("scheidingmateriaal1");
    }

    public static Scheiding getScheidingSample2() {
        return new Scheiding()
            .id(2L)
            .aanleghoogte("aanleghoogte2")
            .breedte("breedte2")
            .hoogte("hoogte2")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd2")
            .lengte("lengte2")
            .leverancier("leverancier2")
            .eobjectnaam("eobjectnaam2")
            .eobjectnummer("eobjectnummer2")
            .oppervlakte("oppervlakte2")
            .scheidingmateriaal("scheidingmateriaal2");
    }

    public static Scheiding getScheidingRandomSampleGenerator() {
        return new Scheiding()
            .id(longCount.incrementAndGet())
            .aanleghoogte(UUID.randomUUID().toString())
            .breedte(UUID.randomUUID().toString())
            .hoogte(UUID.randomUUID().toString())
            .jaaronderhouduitgevoerd(UUID.randomUUID().toString())
            .lengte(UUID.randomUUID().toString())
            .leverancier(UUID.randomUUID().toString())
            .eobjectnaam(UUID.randomUUID().toString())
            .eobjectnummer(UUID.randomUUID().toString())
            .oppervlakte(UUID.randomUUID().toString())
            .scheidingmateriaal(UUID.randomUUID().toString());
    }
}
