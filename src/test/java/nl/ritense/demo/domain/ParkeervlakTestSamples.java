package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ParkeervlakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Parkeervlak getParkeervlakSample1() {
        return new Parkeervlak()
            .id(1L)
            .aantal("aantal1")
            .coordinaten("coordinaten1")
            .doelgroep("doelgroep1")
            .plaats("plaats1")
            .vlakid("vlakid1");
    }

    public static Parkeervlak getParkeervlakSample2() {
        return new Parkeervlak()
            .id(2L)
            .aantal("aantal2")
            .coordinaten("coordinaten2")
            .doelgroep("doelgroep2")
            .plaats("plaats2")
            .vlakid("vlakid2");
    }

    public static Parkeervlak getParkeervlakRandomSampleGenerator() {
        return new Parkeervlak()
            .id(longCount.incrementAndGet())
            .aantal(UUID.randomUUID().toString())
            .coordinaten(UUID.randomUUID().toString())
            .doelgroep(UUID.randomUUID().toString())
            .plaats(UUID.randomUUID().toString())
            .vlakid(UUID.randomUUID().toString());
    }
}
