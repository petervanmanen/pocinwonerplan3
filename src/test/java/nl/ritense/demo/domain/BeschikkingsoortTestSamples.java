package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BeschikkingsoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Beschikkingsoort getBeschikkingsoortSample1() {
        return new Beschikkingsoort().id(1L);
    }

    public static Beschikkingsoort getBeschikkingsoortSample2() {
        return new Beschikkingsoort().id(2L);
    }

    public static Beschikkingsoort getBeschikkingsoortRandomSampleGenerator() {
        return new Beschikkingsoort().id(longCount.incrementAndGet());
    }
}
