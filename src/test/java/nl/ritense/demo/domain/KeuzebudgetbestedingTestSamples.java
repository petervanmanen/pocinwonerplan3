package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class KeuzebudgetbestedingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Keuzebudgetbesteding getKeuzebudgetbestedingSample1() {
        return new Keuzebudgetbesteding().id(1L);
    }

    public static Keuzebudgetbesteding getKeuzebudgetbestedingSample2() {
        return new Keuzebudgetbesteding().id(2L);
    }

    public static Keuzebudgetbesteding getKeuzebudgetbestedingRandomSampleGenerator() {
        return new Keuzebudgetbesteding().id(longCount.incrementAndGet());
    }
}
