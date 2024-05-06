package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DeclaratieregelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Declaratieregel getDeclaratieregelSample1() {
        return new Declaratieregel().id(1L).code("code1");
    }

    public static Declaratieregel getDeclaratieregelSample2() {
        return new Declaratieregel().id(2L).code("code2");
    }

    public static Declaratieregel getDeclaratieregelRandomSampleGenerator() {
        return new Declaratieregel().id(longCount.incrementAndGet()).code(UUID.randomUUID().toString());
    }
}
