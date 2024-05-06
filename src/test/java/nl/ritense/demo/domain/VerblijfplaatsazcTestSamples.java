package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class VerblijfplaatsazcTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verblijfplaatsazc getVerblijfplaatsazcSample1() {
        return new Verblijfplaatsazc().id(1L);
    }

    public static Verblijfplaatsazc getVerblijfplaatsazcSample2() {
        return new Verblijfplaatsazc().id(2L);
    }

    public static Verblijfplaatsazc getVerblijfplaatsazcRandomSampleGenerator() {
        return new Verblijfplaatsazc().id(longCount.incrementAndGet());
    }
}
