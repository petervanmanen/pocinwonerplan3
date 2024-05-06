package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UitvoerendeinstantieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Uitvoerendeinstantie getUitvoerendeinstantieSample1() {
        return new Uitvoerendeinstantie().id(1L).naam("naam1");
    }

    public static Uitvoerendeinstantie getUitvoerendeinstantieSample2() {
        return new Uitvoerendeinstantie().id(2L).naam("naam2");
    }

    public static Uitvoerendeinstantie getUitvoerendeinstantieRandomSampleGenerator() {
        return new Uitvoerendeinstantie().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
