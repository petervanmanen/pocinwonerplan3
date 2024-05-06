package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StemmingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Stemming getStemmingSample1() {
        return new Stemming().id(1L).resultaat("resultaat1").stemmingstype("stemmingstype1");
    }

    public static Stemming getStemmingSample2() {
        return new Stemming().id(2L).resultaat("resultaat2").stemmingstype("stemmingstype2");
    }

    public static Stemming getStemmingRandomSampleGenerator() {
        return new Stemming()
            .id(longCount.incrementAndGet())
            .resultaat(UUID.randomUUID().toString())
            .stemmingstype(UUID.randomUUID().toString());
    }
}
