package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class WinkelverkoopgroepTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Winkelverkoopgroep getWinkelverkoopgroepSample1() {
        return new Winkelverkoopgroep().id(1L);
    }

    public static Winkelverkoopgroep getWinkelverkoopgroepSample2() {
        return new Winkelverkoopgroep().id(2L);
    }

    public static Winkelverkoopgroep getWinkelverkoopgroepRandomSampleGenerator() {
        return new Winkelverkoopgroep().id(longCount.incrementAndGet());
    }
}
