package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BouwdeelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bouwdeel getBouwdeelSample1() {
        return new Bouwdeel().id(1L).code("code1").omschrijving("omschrijving1");
    }

    public static Bouwdeel getBouwdeelSample2() {
        return new Bouwdeel().id(2L).code("code2").omschrijving("omschrijving2");
    }

    public static Bouwdeel getBouwdeelRandomSampleGenerator() {
        return new Bouwdeel().id(longCount.incrementAndGet()).code(UUID.randomUUID().toString()).omschrijving(UUID.randomUUID().toString());
    }
}
