package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ToegangsmiddelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Toegangsmiddel getToegangsmiddelSample1() {
        return new Toegangsmiddel().id(1L);
    }

    public static Toegangsmiddel getToegangsmiddelSample2() {
        return new Toegangsmiddel().id(2L);
    }

    public static Toegangsmiddel getToegangsmiddelRandomSampleGenerator() {
        return new Toegangsmiddel().id(longCount.incrementAndGet());
    }
}
