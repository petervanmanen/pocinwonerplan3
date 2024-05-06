package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class InitiatiefnemerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Initiatiefnemer getInitiatiefnemerSample1() {
        return new Initiatiefnemer().id(1L);
    }

    public static Initiatiefnemer getInitiatiefnemerSample2() {
        return new Initiatiefnemer().id(2L);
    }

    public static Initiatiefnemer getInitiatiefnemerRandomSampleGenerator() {
        return new Initiatiefnemer().id(longCount.incrementAndGet());
    }
}
