package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class VeldTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Veld getVeldSample1() {
        return new Veld().id(1L);
    }

    public static Veld getVeldSample2() {
        return new Veld().id(2L);
    }

    public static Veld getVeldRandomSampleGenerator() {
        return new Veld().id(longCount.incrementAndGet());
    }
}
