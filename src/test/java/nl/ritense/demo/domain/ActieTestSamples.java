package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ActieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Actie getActieSample1() {
        return new Actie().id(1L);
    }

    public static Actie getActieSample2() {
        return new Actie().id(2L);
    }

    public static Actie getActieRandomSampleGenerator() {
        return new Actie().id(longCount.incrementAndGet());
    }
}
