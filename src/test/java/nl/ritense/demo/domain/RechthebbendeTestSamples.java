package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RechthebbendeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Rechthebbende getRechthebbendeSample1() {
        return new Rechthebbende().id(1L);
    }

    public static Rechthebbende getRechthebbendeSample2() {
        return new Rechthebbende().id(2L);
    }

    public static Rechthebbende getRechthebbendeRandomSampleGenerator() {
        return new Rechthebbende().id(longCount.incrementAndGet());
    }
}
