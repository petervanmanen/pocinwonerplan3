package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AanbestedingvastgoedTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aanbestedingvastgoed getAanbestedingvastgoedSample1() {
        return new Aanbestedingvastgoed().id(1L);
    }

    public static Aanbestedingvastgoed getAanbestedingvastgoedSample2() {
        return new Aanbestedingvastgoed().id(2L);
    }

    public static Aanbestedingvastgoed getAanbestedingvastgoedRandomSampleGenerator() {
        return new Aanbestedingvastgoed().id(longCount.incrementAndGet());
    }
}
