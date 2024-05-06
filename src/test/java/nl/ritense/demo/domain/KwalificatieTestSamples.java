package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class KwalificatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kwalificatie getKwalificatieSample1() {
        return new Kwalificatie().id(1L);
    }

    public static Kwalificatie getKwalificatieSample2() {
        return new Kwalificatie().id(2L);
    }

    public static Kwalificatie getKwalificatieRandomSampleGenerator() {
        return new Kwalificatie().id(longCount.incrementAndGet());
    }
}
