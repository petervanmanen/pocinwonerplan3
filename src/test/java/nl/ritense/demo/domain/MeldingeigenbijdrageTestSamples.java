package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class MeldingeigenbijdrageTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Meldingeigenbijdrage getMeldingeigenbijdrageSample1() {
        return new Meldingeigenbijdrage().id(1L);
    }

    public static Meldingeigenbijdrage getMeldingeigenbijdrageSample2() {
        return new Meldingeigenbijdrage().id(2L);
    }

    public static Meldingeigenbijdrage getMeldingeigenbijdrageRandomSampleGenerator() {
        return new Meldingeigenbijdrage().id(longCount.incrementAndGet());
    }
}
