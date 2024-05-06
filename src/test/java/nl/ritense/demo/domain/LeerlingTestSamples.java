package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class LeerlingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Leerling getLeerlingSample1() {
        return new Leerling().id(1L);
    }

    public static Leerling getLeerlingSample2() {
        return new Leerling().id(2L);
    }

    public static Leerling getLeerlingRandomSampleGenerator() {
        return new Leerling().id(longCount.incrementAndGet());
    }
}
