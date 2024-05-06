package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SportparkTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sportpark getSportparkSample1() {
        return new Sportpark().id(1L);
    }

    public static Sportpark getSportparkSample2() {
        return new Sportpark().id(2L);
    }

    public static Sportpark getSportparkRandomSampleGenerator() {
        return new Sportpark().id(longCount.incrementAndGet());
    }
}
