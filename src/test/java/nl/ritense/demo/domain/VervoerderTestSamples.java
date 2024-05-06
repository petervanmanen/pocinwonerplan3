package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class VervoerderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vervoerder getVervoerderSample1() {
        return new Vervoerder().id(1L);
    }

    public static Vervoerder getVervoerderSample2() {
        return new Vervoerder().id(2L);
    }

    public static Vervoerder getVervoerderRandomSampleGenerator() {
        return new Vervoerder().id(longCount.incrementAndGet());
    }
}
