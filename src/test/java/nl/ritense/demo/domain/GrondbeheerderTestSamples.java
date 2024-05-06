package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class GrondbeheerderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Grondbeheerder getGrondbeheerderSample1() {
        return new Grondbeheerder().id(1L);
    }

    public static Grondbeheerder getGrondbeheerderSample2() {
        return new Grondbeheerder().id(2L);
    }

    public static Grondbeheerder getGrondbeheerderRandomSampleGenerator() {
        return new Grondbeheerder().id(longCount.incrementAndGet());
    }
}
