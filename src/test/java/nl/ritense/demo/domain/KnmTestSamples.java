package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class KnmTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Knm getKnmSample1() {
        return new Knm().id(1L);
    }

    public static Knm getKnmSample2() {
        return new Knm().id(2L);
    }

    public static Knm getKnmRandomSampleGenerator() {
        return new Knm().id(longCount.incrementAndGet());
    }
}
