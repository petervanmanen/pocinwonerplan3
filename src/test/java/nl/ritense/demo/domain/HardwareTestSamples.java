package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class HardwareTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Hardware getHardwareSample1() {
        return new Hardware().id(1L);
    }

    public static Hardware getHardwareSample2() {
        return new Hardware().id(2L);
    }

    public static Hardware getHardwareRandomSampleGenerator() {
        return new Hardware().id(longCount.incrementAndGet());
    }
}
