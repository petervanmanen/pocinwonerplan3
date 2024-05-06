package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class StadspasTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Stadspas getStadspasSample1() {
        return new Stadspas().id(1L);
    }

    public static Stadspas getStadspasSample2() {
        return new Stadspas().id(2L);
    }

    public static Stadspas getStadspasRandomSampleGenerator() {
        return new Stadspas().id(longCount.incrementAndGet());
    }
}
