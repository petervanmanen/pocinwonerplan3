package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class DebiteurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Debiteur getDebiteurSample1() {
        return new Debiteur().id(1L);
    }

    public static Debiteur getDebiteurSample2() {
        return new Debiteur().id(2L);
    }

    public static Debiteur getDebiteurRandomSampleGenerator() {
        return new Debiteur().id(longCount.incrementAndGet());
    }
}
