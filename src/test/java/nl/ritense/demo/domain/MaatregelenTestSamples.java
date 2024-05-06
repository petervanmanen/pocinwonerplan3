package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class MaatregelenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Maatregelen getMaatregelenSample1() {
        return new Maatregelen().id(1L);
    }

    public static Maatregelen getMaatregelenSample2() {
        return new Maatregelen().id(2L);
    }

    public static Maatregelen getMaatregelenRandomSampleGenerator() {
        return new Maatregelen().id(longCount.incrementAndGet());
    }
}
