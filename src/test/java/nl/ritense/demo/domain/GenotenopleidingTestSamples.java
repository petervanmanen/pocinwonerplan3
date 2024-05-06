package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GenotenopleidingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Genotenopleiding getGenotenopleidingSample1() {
        return new Genotenopleiding().id(1L).verrekenen("verrekenen1");
    }

    public static Genotenopleiding getGenotenopleidingSample2() {
        return new Genotenopleiding().id(2L).verrekenen("verrekenen2");
    }

    public static Genotenopleiding getGenotenopleidingRandomSampleGenerator() {
        return new Genotenopleiding().id(longCount.incrementAndGet()).verrekenen(UUID.randomUUID().toString());
    }
}
