package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OuderofverzorgerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ouderofverzorger getOuderofverzorgerSample1() {
        return new Ouderofverzorger().id(1L);
    }

    public static Ouderofverzorger getOuderofverzorgerSample2() {
        return new Ouderofverzorger().id(2L);
    }

    public static Ouderofverzorger getOuderofverzorgerRandomSampleGenerator() {
        return new Ouderofverzorger().id(longCount.incrementAndGet());
    }
}
