package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class LicentieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Licentie getLicentieSample1() {
        return new Licentie().id(1L);
    }

    public static Licentie getLicentieSample2() {
        return new Licentie().id(2L);
    }

    public static Licentie getLicentieRandomSampleGenerator() {
        return new Licentie().id(longCount.incrementAndGet());
    }
}
