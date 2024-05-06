package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LeidingelementTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Leidingelement getLeidingelementSample1() {
        return new Leidingelement()
            .id(1L)
            .afwijkendedieptelegging("afwijkendedieptelegging1")
            .diepte("diepte1")
            .geonauwkeurigheidxy("geonauwkeurigheidxy1")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd1")
            .leverancier("leverancier1")
            .themaimkl("themaimkl1");
    }

    public static Leidingelement getLeidingelementSample2() {
        return new Leidingelement()
            .id(2L)
            .afwijkendedieptelegging("afwijkendedieptelegging2")
            .diepte("diepte2")
            .geonauwkeurigheidxy("geonauwkeurigheidxy2")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd2")
            .leverancier("leverancier2")
            .themaimkl("themaimkl2");
    }

    public static Leidingelement getLeidingelementRandomSampleGenerator() {
        return new Leidingelement()
            .id(longCount.incrementAndGet())
            .afwijkendedieptelegging(UUID.randomUUID().toString())
            .diepte(UUID.randomUUID().toString())
            .geonauwkeurigheidxy(UUID.randomUUID().toString())
            .jaaronderhouduitgevoerd(UUID.randomUUID().toString())
            .leverancier(UUID.randomUUID().toString())
            .themaimkl(UUID.randomUUID().toString());
    }
}
