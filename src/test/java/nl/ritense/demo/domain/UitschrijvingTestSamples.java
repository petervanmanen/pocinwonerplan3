package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class UitschrijvingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Uitschrijving getUitschrijvingSample1() {
        return new Uitschrijving().id(1L);
    }

    public static Uitschrijving getUitschrijvingSample2() {
        return new Uitschrijving().id(2L);
    }

    public static Uitschrijving getUitschrijvingRandomSampleGenerator() {
        return new Uitschrijving().id(longCount.incrementAndGet());
    }
}
