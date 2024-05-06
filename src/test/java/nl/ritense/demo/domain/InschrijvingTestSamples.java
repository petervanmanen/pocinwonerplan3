package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class InschrijvingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Inschrijving getInschrijvingSample1() {
        return new Inschrijving().id(1L);
    }

    public static Inschrijving getInschrijvingSample2() {
        return new Inschrijving().id(2L);
    }

    public static Inschrijving getInschrijvingRandomSampleGenerator() {
        return new Inschrijving().id(longCount.incrementAndGet());
    }
}
