package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class VervoersmiddelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vervoersmiddel getVervoersmiddelSample1() {
        return new Vervoersmiddel().id(1L);
    }

    public static Vervoersmiddel getVervoersmiddelSample2() {
        return new Vervoersmiddel().id(2L);
    }

    public static Vervoersmiddel getVervoersmiddelRandomSampleGenerator() {
        return new Vervoersmiddel().id(longCount.incrementAndGet());
    }
}
