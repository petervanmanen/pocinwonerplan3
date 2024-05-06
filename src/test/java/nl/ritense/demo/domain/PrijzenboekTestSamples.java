package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PrijzenboekTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Prijzenboek getPrijzenboekSample1() {
        return new Prijzenboek().id(1L);
    }

    public static Prijzenboek getPrijzenboekSample2() {
        return new Prijzenboek().id(2L);
    }

    public static Prijzenboek getPrijzenboekRandomSampleGenerator() {
        return new Prijzenboek().id(longCount.incrementAndGet());
    }
}
