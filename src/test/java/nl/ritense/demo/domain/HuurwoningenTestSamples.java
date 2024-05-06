package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class HuurwoningenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Huurwoningen getHuurwoningenSample1() {
        return new Huurwoningen().id(1L);
    }

    public static Huurwoningen getHuurwoningenSample2() {
        return new Huurwoningen().id(2L);
    }

    public static Huurwoningen getHuurwoningenRandomSampleGenerator() {
        return new Huurwoningen().id(longCount.incrementAndGet());
    }
}
