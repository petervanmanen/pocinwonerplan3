package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class IndividueelkeuzebudgetTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Individueelkeuzebudget getIndividueelkeuzebudgetSample1() {
        return new Individueelkeuzebudget().id(1L);
    }

    public static Individueelkeuzebudget getIndividueelkeuzebudgetSample2() {
        return new Individueelkeuzebudget().id(2L);
    }

    public static Individueelkeuzebudget getIndividueelkeuzebudgetRandomSampleGenerator() {
        return new Individueelkeuzebudget().id(longCount.incrementAndGet());
    }
}
