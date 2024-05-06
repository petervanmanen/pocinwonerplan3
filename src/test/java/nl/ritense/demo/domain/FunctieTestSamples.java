package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FunctieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Functie getFunctieSample1() {
        return new Functie().id(1L).groep("groep1").naam("naam1");
    }

    public static Functie getFunctieSample2() {
        return new Functie().id(2L).groep("groep2").naam("naam2");
    }

    public static Functie getFunctieRandomSampleGenerator() {
        return new Functie().id(longCount.incrementAndGet()).groep(UUID.randomUUID().toString()).naam(UUID.randomUUID().toString());
    }
}
