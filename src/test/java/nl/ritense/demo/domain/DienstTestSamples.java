package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class DienstTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Dienst getDienstSample1() {
        return new Dienst().id(1L);
    }

    public static Dienst getDienstSample2() {
        return new Dienst().id(2L);
    }

    public static Dienst getDienstRandomSampleGenerator() {
        return new Dienst().id(longCount.incrementAndGet());
    }
}
