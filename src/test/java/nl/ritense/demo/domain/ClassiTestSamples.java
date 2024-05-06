package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ClassiTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Classi getClassiSample1() {
        return new Classi().id(1L);
    }

    public static Classi getClassiSample2() {
        return new Classi().id(2L);
    }

    public static Classi getClassiRandomSampleGenerator() {
        return new Classi().id(longCount.incrementAndGet());
    }
}
