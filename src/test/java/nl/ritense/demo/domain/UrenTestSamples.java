package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UrenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Uren getUrenSample1() {
        return new Uren().id(1L).aantal("aantal1");
    }

    public static Uren getUrenSample2() {
        return new Uren().id(2L).aantal("aantal2");
    }

    public static Uren getUrenRandomSampleGenerator() {
        return new Uren().id(longCount.incrementAndGet()).aantal(UUID.randomUUID().toString());
    }
}
