package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PrijsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Prijs getPrijsSample1() {
        return new Prijs().id(1L);
    }

    public static Prijs getPrijsSample2() {
        return new Prijs().id(2L);
    }

    public static Prijs getPrijsRandomSampleGenerator() {
        return new Prijs().id(longCount.incrementAndGet());
    }
}
