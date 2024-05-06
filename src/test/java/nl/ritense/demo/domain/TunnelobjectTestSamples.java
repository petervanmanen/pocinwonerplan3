package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TunnelobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Tunnelobject getTunnelobjectSample1() {
        return new Tunnelobject()
            .id(1L)
            .aanleghoogte("aanleghoogte1")
            .aantaltunnelbuizen("aantaltunnelbuizen1")
            .breedte("breedte1")
            .doorrijbreedte("doorrijbreedte1")
            .doorrijhoogte("doorrijhoogte1")
            .hoogte("hoogte1")
            .jaarconserveren("jaarconserveren1")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd1")
            .lengte("lengte1")
            .leverancier("leverancier1")
            .eobjectnaam("eobjectnaam1")
            .eobjectnummer("eobjectnummer1")
            .oppervlakte("oppervlakte1")
            .tunnelobjectmateriaal("tunnelobjectmateriaal1");
    }

    public static Tunnelobject getTunnelobjectSample2() {
        return new Tunnelobject()
            .id(2L)
            .aanleghoogte("aanleghoogte2")
            .aantaltunnelbuizen("aantaltunnelbuizen2")
            .breedte("breedte2")
            .doorrijbreedte("doorrijbreedte2")
            .doorrijhoogte("doorrijhoogte2")
            .hoogte("hoogte2")
            .jaarconserveren("jaarconserveren2")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd2")
            .lengte("lengte2")
            .leverancier("leverancier2")
            .eobjectnaam("eobjectnaam2")
            .eobjectnummer("eobjectnummer2")
            .oppervlakte("oppervlakte2")
            .tunnelobjectmateriaal("tunnelobjectmateriaal2");
    }

    public static Tunnelobject getTunnelobjectRandomSampleGenerator() {
        return new Tunnelobject()
            .id(longCount.incrementAndGet())
            .aanleghoogte(UUID.randomUUID().toString())
            .aantaltunnelbuizen(UUID.randomUUID().toString())
            .breedte(UUID.randomUUID().toString())
            .doorrijbreedte(UUID.randomUUID().toString())
            .doorrijhoogte(UUID.randomUUID().toString())
            .hoogte(UUID.randomUUID().toString())
            .jaarconserveren(UUID.randomUUID().toString())
            .jaaronderhouduitgevoerd(UUID.randomUUID().toString())
            .lengte(UUID.randomUUID().toString())
            .leverancier(UUID.randomUUID().toString())
            .eobjectnaam(UUID.randomUUID().toString())
            .eobjectnummer(UUID.randomUUID().toString())
            .oppervlakte(UUID.randomUUID().toString())
            .tunnelobjectmateriaal(UUID.randomUUID().toString());
    }
}
