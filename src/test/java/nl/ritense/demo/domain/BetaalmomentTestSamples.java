package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BetaalmomentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Betaalmoment getBetaalmomentSample1() {
        return new Betaalmoment().id(1L);
    }

    public static Betaalmoment getBetaalmomentSample2() {
        return new Betaalmoment().id(2L);
    }

    public static Betaalmoment getBetaalmomentRandomSampleGenerator() {
        return new Betaalmoment().id(longCount.incrementAndGet());
    }
}
