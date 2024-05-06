package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class MapTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Map getMapSample1() {
        return new Map().id(1L);
    }

    public static Map getMapSample2() {
        return new Map().id(2L);
    }

    public static Map getMapRandomSampleGenerator() {
        return new Map().id(longCount.incrementAndGet());
    }
}
