package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ClassfTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Classf getClassfSample1() {
        return new Classf().id(1L);
    }

    public static Classf getClassfSample2() {
        return new Classf().id(2L);
    }

    public static Classf getClassfRandomSampleGenerator() {
        return new Classf().id(longCount.incrementAndGet());
    }
}
