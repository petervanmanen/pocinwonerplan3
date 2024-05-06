package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BezettingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bezetting getBezettingSample1() {
        return new Bezetting().id(1L);
    }

    public static Bezetting getBezettingSample2() {
        return new Bezetting().id(2L);
    }

    public static Bezetting getBezettingRandomSampleGenerator() {
        return new Bezetting().id(longCount.incrementAndGet());
    }
}
