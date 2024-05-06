package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ClasshTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Classh getClasshSample1() {
        return new Classh().id(1L);
    }

    public static Classh getClasshSample2() {
        return new Classh().id(2L);
    }

    public static Classh getClasshRandomSampleGenerator() {
        return new Classh().id(longCount.incrementAndGet());
    }
}
