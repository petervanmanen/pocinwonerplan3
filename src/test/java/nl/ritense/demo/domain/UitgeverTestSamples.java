package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class UitgeverTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Uitgever getUitgeverSample1() {
        return new Uitgever().id(1L);
    }

    public static Uitgever getUitgeverSample2() {
        return new Uitgever().id(2L);
    }

    public static Uitgever getUitgeverRandomSampleGenerator() {
        return new Uitgever().id(longCount.incrementAndGet());
    }
}
