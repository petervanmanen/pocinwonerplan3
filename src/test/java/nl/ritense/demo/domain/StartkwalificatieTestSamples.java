package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class StartkwalificatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Startkwalificatie getStartkwalificatieSample1() {
        return new Startkwalificatie().id(1L);
    }

    public static Startkwalificatie getStartkwalificatieSample2() {
        return new Startkwalificatie().id(2L);
    }

    public static Startkwalificatie getStartkwalificatieRandomSampleGenerator() {
        return new Startkwalificatie().id(longCount.incrementAndGet());
    }
}
