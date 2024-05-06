package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class KwaliteitskenmerkenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kwaliteitskenmerken getKwaliteitskenmerkenSample1() {
        return new Kwaliteitskenmerken().id(1L);
    }

    public static Kwaliteitskenmerken getKwaliteitskenmerkenSample2() {
        return new Kwaliteitskenmerken().id(2L);
    }

    public static Kwaliteitskenmerken getKwaliteitskenmerkenRandomSampleGenerator() {
        return new Kwaliteitskenmerken().id(longCount.incrementAndGet());
    }
}
