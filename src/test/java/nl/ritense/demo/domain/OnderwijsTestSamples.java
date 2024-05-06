package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OnderwijsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Onderwijs getOnderwijsSample1() {
        return new Onderwijs().id(1L);
    }

    public static Onderwijs getOnderwijsSample2() {
        return new Onderwijs().id(2L);
    }

    public static Onderwijs getOnderwijsRandomSampleGenerator() {
        return new Onderwijs().id(longCount.incrementAndGet());
    }
}
