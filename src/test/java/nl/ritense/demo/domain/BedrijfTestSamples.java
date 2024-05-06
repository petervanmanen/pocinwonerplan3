package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BedrijfTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bedrijf getBedrijfSample1() {
        return new Bedrijf().id(1L);
    }

    public static Bedrijf getBedrijfSample2() {
        return new Bedrijf().id(2L);
    }

    public static Bedrijf getBedrijfRandomSampleGenerator() {
        return new Bedrijf().id(longCount.incrementAndGet());
    }
}
