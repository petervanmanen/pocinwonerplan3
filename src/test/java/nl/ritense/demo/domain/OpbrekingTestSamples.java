package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OpbrekingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Opbreking getOpbrekingSample1() {
        return new Opbreking().id(1L);
    }

    public static Opbreking getOpbrekingSample2() {
        return new Opbreking().id(2L);
    }

    public static Opbreking getOpbrekingRandomSampleGenerator() {
        return new Opbreking().id(longCount.incrementAndGet());
    }
}
