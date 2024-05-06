package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class B1TestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static B1 getB1Sample1() {
        return new B1().id(1L);
    }

    public static B1 getB1Sample2() {
        return new B1().id(2L);
    }

    public static B1 getB1RandomSampleGenerator() {
        return new B1().id(longCount.incrementAndGet());
    }
}
