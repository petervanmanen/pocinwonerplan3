package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InstallatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Installatie getInstallatieSample1() {
        return new Installatie()
            .id(1L)
            .breedte("breedte1")
            .eancode("eancode1")
            .fabrikant("fabrikant1")
            .hoogte("hoogte1")
            .inbelgegevens("inbelgegevens1")
            .installateur("installateur1")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd1")
            .lengte("lengte1")
            .leverancier("leverancier1")
            .typecommunicatie("typecommunicatie1");
    }

    public static Installatie getInstallatieSample2() {
        return new Installatie()
            .id(2L)
            .breedte("breedte2")
            .eancode("eancode2")
            .fabrikant("fabrikant2")
            .hoogte("hoogte2")
            .inbelgegevens("inbelgegevens2")
            .installateur("installateur2")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd2")
            .lengte("lengte2")
            .leverancier("leverancier2")
            .typecommunicatie("typecommunicatie2");
    }

    public static Installatie getInstallatieRandomSampleGenerator() {
        return new Installatie()
            .id(longCount.incrementAndGet())
            .breedte(UUID.randomUUID().toString())
            .eancode(UUID.randomUUID().toString())
            .fabrikant(UUID.randomUUID().toString())
            .hoogte(UUID.randomUUID().toString())
            .inbelgegevens(UUID.randomUUID().toString())
            .installateur(UUID.randomUUID().toString())
            .jaaronderhouduitgevoerd(UUID.randomUUID().toString())
            .lengte(UUID.randomUUID().toString())
            .leverancier(UUID.randomUUID().toString())
            .typecommunicatie(UUID.randomUUID().toString());
    }
}
