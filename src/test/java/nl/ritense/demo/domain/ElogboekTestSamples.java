package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ElogboekTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Elogboek getElogboekSample1() {
        return new Elogboek().id(1L);
    }

    public static Elogboek getElogboekSample2() {
        return new Elogboek().id(2L);
    }

    public static Elogboek getElogboekRandomSampleGenerator() {
        return new Elogboek().id(longCount.incrementAndGet());
    }
}
