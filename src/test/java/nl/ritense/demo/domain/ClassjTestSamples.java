package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ClassjTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Classj getClassjSample1() {
        return new Classj().id(1L);
    }

    public static Classj getClassjSample2() {
        return new Classj().id(2L);
    }

    public static Classj getClassjRandomSampleGenerator() {
        return new Classj().id(longCount.incrementAndGet());
    }
}
