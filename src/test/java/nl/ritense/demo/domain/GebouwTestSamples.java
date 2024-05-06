package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GebouwTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Gebouw getGebouwSample1() {
        return new Gebouw()
            .id(1L)
            .aantal("aantal1")
            .aantaladressen("aantaladressen1")
            .aantalkamers("aantalkamers1")
            .energielabel("energielabel1")
            .oppervlakte("oppervlakte1");
    }

    public static Gebouw getGebouwSample2() {
        return new Gebouw()
            .id(2L)
            .aantal("aantal2")
            .aantaladressen("aantaladressen2")
            .aantalkamers("aantalkamers2")
            .energielabel("energielabel2")
            .oppervlakte("oppervlakte2");
    }

    public static Gebouw getGebouwRandomSampleGenerator() {
        return new Gebouw()
            .id(longCount.incrementAndGet())
            .aantal(UUID.randomUUID().toString())
            .aantaladressen(UUID.randomUUID().toString())
            .aantalkamers(UUID.randomUUID().toString())
            .energielabel(UUID.randomUUID().toString())
            .oppervlakte(UUID.randomUUID().toString());
    }
}
