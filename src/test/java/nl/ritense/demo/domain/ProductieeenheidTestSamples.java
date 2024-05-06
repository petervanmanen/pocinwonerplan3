package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ProductieeenheidTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Productieeenheid getProductieeenheidSample1() {
        return new Productieeenheid().id(1L);
    }

    public static Productieeenheid getProductieeenheidSample2() {
        return new Productieeenheid().id(2L);
    }

    public static Productieeenheid getProductieeenheidRandomSampleGenerator() {
        return new Productieeenheid().id(longCount.incrementAndGet());
    }
}
