package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BudgetuitputtingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Budgetuitputting getBudgetuitputtingSample1() {
        return new Budgetuitputting().id(1L);
    }

    public static Budgetuitputting getBudgetuitputtingSample2() {
        return new Budgetuitputting().id(2L);
    }

    public static Budgetuitputting getBudgetuitputtingRandomSampleGenerator() {
        return new Budgetuitputting().id(longCount.incrementAndGet());
    }
}
