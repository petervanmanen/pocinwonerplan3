package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class HuishoudenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Huishouden getHuishoudenSample1() {
        return new Huishouden().id(1L);
    }

    public static Huishouden getHuishoudenSample2() {
        return new Huishouden().id(2L);
    }

    public static Huishouden getHuishoudenRandomSampleGenerator() {
        return new Huishouden().id(longCount.incrementAndGet());
    }
}
