package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OnderhoudTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Onderhoud getOnderhoudSample1() {
        return new Onderhoud().id(1L);
    }

    public static Onderhoud getOnderhoudSample2() {
        return new Onderhoud().id(2L);
    }

    public static Onderhoud getOnderhoudRandomSampleGenerator() {
        return new Onderhoud().id(longCount.incrementAndGet());
    }
}
