package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class IndieningsvereistenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Indieningsvereisten getIndieningsvereistenSample1() {
        return new Indieningsvereisten().id(1L);
    }

    public static Indieningsvereisten getIndieningsvereistenSample2() {
        return new Indieningsvereisten().id(2L);
    }

    public static Indieningsvereisten getIndieningsvereistenRandomSampleGenerator() {
        return new Indieningsvereisten().id(longCount.incrementAndGet());
    }
}
