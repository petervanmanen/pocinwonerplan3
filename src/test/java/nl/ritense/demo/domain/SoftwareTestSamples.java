package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SoftwareTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Software getSoftwareSample1() {
        return new Software().id(1L);
    }

    public static Software getSoftwareSample2() {
        return new Software().id(2L);
    }

    public static Software getSoftwareRandomSampleGenerator() {
        return new Software().id(longCount.incrementAndGet());
    }
}
