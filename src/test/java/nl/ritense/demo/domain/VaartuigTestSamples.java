package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VaartuigTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vaartuig getVaartuigSample1() {
        return new Vaartuig()
            .id(1L)
            .breedte("breedte1")
            .hoogte("hoogte1")
            .kleur("kleur1")
            .lengte("lengte1")
            .naamvaartuig("naamvaartuig1")
            .registratienummer("registratienummer1");
    }

    public static Vaartuig getVaartuigSample2() {
        return new Vaartuig()
            .id(2L)
            .breedte("breedte2")
            .hoogte("hoogte2")
            .kleur("kleur2")
            .lengte("lengte2")
            .naamvaartuig("naamvaartuig2")
            .registratienummer("registratienummer2");
    }

    public static Vaartuig getVaartuigRandomSampleGenerator() {
        return new Vaartuig()
            .id(longCount.incrementAndGet())
            .breedte(UUID.randomUUID().toString())
            .hoogte(UUID.randomUUID().toString())
            .kleur(UUID.randomUUID().toString())
            .lengte(UUID.randomUUID().toString())
            .naamvaartuig(UUID.randomUUID().toString())
            .registratienummer(UUID.randomUUID().toString());
    }
}
