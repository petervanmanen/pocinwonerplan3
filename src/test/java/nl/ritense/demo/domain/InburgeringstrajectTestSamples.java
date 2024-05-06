package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class InburgeringstrajectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Inburgeringstraject getInburgeringstrajectSample1() {
        return new Inburgeringstraject().id(1L);
    }

    public static Inburgeringstraject getInburgeringstrajectSample2() {
        return new Inburgeringstraject().id(2L);
    }

    public static Inburgeringstraject getInburgeringstrajectRandomSampleGenerator() {
        return new Inburgeringstraject().id(longCount.incrementAndGet());
    }
}
