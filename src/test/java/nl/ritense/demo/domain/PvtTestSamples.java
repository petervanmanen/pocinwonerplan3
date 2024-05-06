package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PvtTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Pvt getPvtSample1() {
        return new Pvt().id(1L);
    }

    public static Pvt getPvtSample2() {
        return new Pvt().id(2L);
    }

    public static Pvt getPvtRandomSampleGenerator() {
        return new Pvt().id(longCount.incrementAndGet());
    }
}
