package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WinkelvoorraaditemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Winkelvoorraaditem getWinkelvoorraaditemSample1() {
        return new Winkelvoorraaditem().id(1L).aantal("aantal1").aantalinbestelling("aantalinbestelling1").locatie("locatie1");
    }

    public static Winkelvoorraaditem getWinkelvoorraaditemSample2() {
        return new Winkelvoorraaditem().id(2L).aantal("aantal2").aantalinbestelling("aantalinbestelling2").locatie("locatie2");
    }

    public static Winkelvoorraaditem getWinkelvoorraaditemRandomSampleGenerator() {
        return new Winkelvoorraaditem()
            .id(longCount.incrementAndGet())
            .aantal(UUID.randomUUID().toString())
            .aantalinbestelling(UUID.randomUUID().toString())
            .locatie(UUID.randomUUID().toString());
    }
}
