package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NormTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Norm getNormSample1() {
        return new Norm().id(1L).nen3610id("nen3610id1");
    }

    public static Norm getNormSample2() {
        return new Norm().id(2L).nen3610id("nen3610id2");
    }

    public static Norm getNormRandomSampleGenerator() {
        return new Norm().id(longCount.incrementAndGet()).nen3610id(UUID.randomUUID().toString());
    }
}
