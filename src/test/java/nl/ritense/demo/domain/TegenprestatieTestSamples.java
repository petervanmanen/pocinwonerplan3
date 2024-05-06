package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TegenprestatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Tegenprestatie getTegenprestatieSample1() {
        return new Tegenprestatie().id(1L);
    }

    public static Tegenprestatie getTegenprestatieSample2() {
        return new Tegenprestatie().id(2L);
    }

    public static Tegenprestatie getTegenprestatieRandomSampleGenerator() {
        return new Tegenprestatie().id(longCount.incrementAndGet());
    }
}
