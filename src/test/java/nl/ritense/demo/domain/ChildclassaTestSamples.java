package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ChildclassaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Childclassa getChildclassaSample1() {
        return new Childclassa().id(1L).kleur("kleur1");
    }

    public static Childclassa getChildclassaSample2() {
        return new Childclassa().id(2L).kleur("kleur2");
    }

    public static Childclassa getChildclassaRandomSampleGenerator() {
        return new Childclassa().id(longCount.incrementAndGet()).kleur(UUID.randomUUID().toString());
    }
}
