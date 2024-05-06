package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class InventarisTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Inventaris getInventarisSample1() {
        return new Inventaris().id(1L);
    }

    public static Inventaris getInventarisSample2() {
        return new Inventaris().id(2L);
    }

    public static Inventaris getInventarisRandomSampleGenerator() {
        return new Inventaris().id(longCount.incrementAndGet());
    }
}
