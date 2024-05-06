package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SamenstellerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Samensteller getSamenstellerSample1() {
        return new Samensteller().id(1L).rol("rol1");
    }

    public static Samensteller getSamenstellerSample2() {
        return new Samensteller().id(2L).rol("rol2");
    }

    public static Samensteller getSamenstellerRandomSampleGenerator() {
        return new Samensteller().id(longCount.incrementAndGet()).rol(UUID.randomUUID().toString());
    }
}
