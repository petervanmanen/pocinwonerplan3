package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OnderwijsniveauTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Onderwijsniveau getOnderwijsniveauSample1() {
        return new Onderwijsniveau().id(1L);
    }

    public static Onderwijsniveau getOnderwijsniveauSample2() {
        return new Onderwijsniveau().id(2L);
    }

    public static Onderwijsniveau getOnderwijsniveauRandomSampleGenerator() {
        return new Onderwijsniveau().id(longCount.incrementAndGet());
    }
}
