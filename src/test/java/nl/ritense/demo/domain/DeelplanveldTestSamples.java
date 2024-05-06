package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class DeelplanveldTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Deelplanveld getDeelplanveldSample1() {
        return new Deelplanveld().id(1L);
    }

    public static Deelplanveld getDeelplanveldSample2() {
        return new Deelplanveld().id(2L);
    }

    public static Deelplanveld getDeelplanveldRandomSampleGenerator() {
        return new Deelplanveld().id(longCount.incrementAndGet());
    }
}
