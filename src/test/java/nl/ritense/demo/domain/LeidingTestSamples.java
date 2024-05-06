package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LeidingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Leiding getLeidingSample1() {
        return new Leiding()
            .id(1L)
            .afwijkendedieptelegging("afwijkendedieptelegging1")
            .breedte("breedte1")
            .diameter("diameter1")
            .diepte("diepte1")
            .eisvoorzorgsmaatregel("eisvoorzorgsmaatregel1")
            .geonauwkeurigheidxy("geonauwkeurigheidxy1")
            .hoogte("hoogte1")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd1")
            .lengte("lengte1")
            .leverancier("leverancier1")
            .materiaal("materiaal1")
            .themaimkl("themaimkl1")
            .verhoogdrisico("verhoogdrisico1");
    }

    public static Leiding getLeidingSample2() {
        return new Leiding()
            .id(2L)
            .afwijkendedieptelegging("afwijkendedieptelegging2")
            .breedte("breedte2")
            .diameter("diameter2")
            .diepte("diepte2")
            .eisvoorzorgsmaatregel("eisvoorzorgsmaatregel2")
            .geonauwkeurigheidxy("geonauwkeurigheidxy2")
            .hoogte("hoogte2")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd2")
            .lengte("lengte2")
            .leverancier("leverancier2")
            .materiaal("materiaal2")
            .themaimkl("themaimkl2")
            .verhoogdrisico("verhoogdrisico2");
    }

    public static Leiding getLeidingRandomSampleGenerator() {
        return new Leiding()
            .id(longCount.incrementAndGet())
            .afwijkendedieptelegging(UUID.randomUUID().toString())
            .breedte(UUID.randomUUID().toString())
            .diameter(UUID.randomUUID().toString())
            .diepte(UUID.randomUUID().toString())
            .eisvoorzorgsmaatregel(UUID.randomUUID().toString())
            .geonauwkeurigheidxy(UUID.randomUUID().toString())
            .hoogte(UUID.randomUUID().toString())
            .jaaronderhouduitgevoerd(UUID.randomUUID().toString())
            .lengte(UUID.randomUUID().toString())
            .leverancier(UUID.randomUUID().toString())
            .materiaal(UUID.randomUUID().toString())
            .themaimkl(UUID.randomUUID().toString())
            .verhoogdrisico(UUID.randomUUID().toString());
    }
}
