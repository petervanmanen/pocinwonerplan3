package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TaakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Taak getTaakSample1() {
        return new Taak().id(1L);
    }

    public static Taak getTaakSample2() {
        return new Taak().id(2L);
    }

    public static Taak getTaakRandomSampleGenerator() {
        return new Taak().id(longCount.incrementAndGet());
    }
}
