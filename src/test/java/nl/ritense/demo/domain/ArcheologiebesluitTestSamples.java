package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ArcheologiebesluitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Archeologiebesluit getArcheologiebesluitSample1() {
        return new Archeologiebesluit().id(1L);
    }

    public static Archeologiebesluit getArcheologiebesluitSample2() {
        return new Archeologiebesluit().id(2L);
    }

    public static Archeologiebesluit getArcheologiebesluitRandomSampleGenerator() {
        return new Archeologiebesluit().id(longCount.incrementAndGet());
    }
}
