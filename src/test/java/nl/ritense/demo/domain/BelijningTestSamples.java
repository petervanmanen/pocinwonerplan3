package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BelijningTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Belijning getBelijningSample1() {
        return new Belijning().id(1L).naam("naam1");
    }

    public static Belijning getBelijningSample2() {
        return new Belijning().id(2L).naam("naam2");
    }

    public static Belijning getBelijningRandomSampleGenerator() {
        return new Belijning().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
