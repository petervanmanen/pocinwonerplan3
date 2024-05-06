package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class KoopwoningenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Koopwoningen getKoopwoningenSample1() {
        return new Koopwoningen().id(1L);
    }

    public static Koopwoningen getKoopwoningenSample2() {
        return new Koopwoningen().id(2L);
    }

    public static Koopwoningen getKoopwoningenRandomSampleGenerator() {
        return new Koopwoningen().id(longCount.incrementAndGet());
    }
}
