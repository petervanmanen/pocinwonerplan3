package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class VerblijfplaatsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verblijfplaats getVerblijfplaatsSample1() {
        return new Verblijfplaats().id(1L);
    }

    public static Verblijfplaats getVerblijfplaatsSample2() {
        return new Verblijfplaats().id(2L);
    }

    public static Verblijfplaats getVerblijfplaatsRandomSampleGenerator() {
        return new Verblijfplaats().id(longCount.incrementAndGet());
    }
}
