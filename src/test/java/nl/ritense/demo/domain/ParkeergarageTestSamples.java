package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ParkeergarageTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Parkeergarage getParkeergarageSample1() {
        return new Parkeergarage().id(1L);
    }

    public static Parkeergarage getParkeergarageSample2() {
        return new Parkeergarage().id(2L);
    }

    public static Parkeergarage getParkeergarageRandomSampleGenerator() {
        return new Parkeergarage().id(longCount.incrementAndGet());
    }
}
