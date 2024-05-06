package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class LeerrouteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Leerroute getLeerrouteSample1() {
        return new Leerroute().id(1L);
    }

    public static Leerroute getLeerrouteSample2() {
        return new Leerroute().id(2L);
    }

    public static Leerroute getLeerrouteRandomSampleGenerator() {
        return new Leerroute().id(longCount.incrementAndGet());
    }
}
