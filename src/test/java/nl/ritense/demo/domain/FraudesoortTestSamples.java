package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FraudesoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Fraudesoort getFraudesoortSample1() {
        return new Fraudesoort().id(1L).naam("naam1");
    }

    public static Fraudesoort getFraudesoortSample2() {
        return new Fraudesoort().id(2L).naam("naam2");
    }

    public static Fraudesoort getFraudesoortRandomSampleGenerator() {
        return new Fraudesoort().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
