package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class HuurderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Huurder getHuurderSample1() {
        return new Huurder().id(1L);
    }

    public static Huurder getHuurderSample2() {
        return new Huurder().id(2L);
    }

    public static Huurder getHuurderRandomSampleGenerator() {
        return new Huurder().id(longCount.incrementAndGet());
    }
}
