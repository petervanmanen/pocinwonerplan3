package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class KpbetrokkenbijTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kpbetrokkenbij getKpbetrokkenbijSample1() {
        return new Kpbetrokkenbij().id(1L);
    }

    public static Kpbetrokkenbij getKpbetrokkenbijSample2() {
        return new Kpbetrokkenbij().id(2L);
    }

    public static Kpbetrokkenbij getKpbetrokkenbijRandomSampleGenerator() {
        return new Kpbetrokkenbij().id(longCount.incrementAndGet());
    }
}
