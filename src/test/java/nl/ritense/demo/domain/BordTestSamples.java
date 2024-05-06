package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BordTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bord getBordSample1() {
        return new Bord()
            .id(1L)
            .breedte("breedte1")
            .diameter("diameter1")
            .drager("drager1")
            .hoogte("hoogte1")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd1")
            .lengte("lengte1")
            .leverancier("leverancier1")
            .materiaal("materiaal1")
            .vorm("vorm1");
    }

    public static Bord getBordSample2() {
        return new Bord()
            .id(2L)
            .breedte("breedte2")
            .diameter("diameter2")
            .drager("drager2")
            .hoogte("hoogte2")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd2")
            .lengte("lengte2")
            .leverancier("leverancier2")
            .materiaal("materiaal2")
            .vorm("vorm2");
    }

    public static Bord getBordRandomSampleGenerator() {
        return new Bord()
            .id(longCount.incrementAndGet())
            .breedte(UUID.randomUUID().toString())
            .diameter(UUID.randomUUID().toString())
            .drager(UUID.randomUUID().toString())
            .hoogte(UUID.randomUUID().toString())
            .jaaronderhouduitgevoerd(UUID.randomUUID().toString())
            .lengte(UUID.randomUUID().toString())
            .leverancier(UUID.randomUUID().toString())
            .materiaal(UUID.randomUUID().toString())
            .vorm(UUID.randomUUID().toString());
    }
}
