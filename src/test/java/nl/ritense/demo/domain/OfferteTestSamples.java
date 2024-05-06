package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OfferteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Offerte getOfferteSample1() {
        return new Offerte().id(1L);
    }

    public static Offerte getOfferteSample2() {
        return new Offerte().id(2L);
    }

    public static Offerte getOfferteRandomSampleGenerator() {
        return new Offerte().id(longCount.incrementAndGet());
    }
}
