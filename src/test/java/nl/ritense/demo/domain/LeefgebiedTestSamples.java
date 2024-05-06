package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LeefgebiedTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Leefgebied getLeefgebiedSample1() {
        return new Leefgebied().id(1L).naam("naam1");
    }

    public static Leefgebied getLeefgebiedSample2() {
        return new Leefgebied().id(2L).naam("naam2");
    }

    public static Leefgebied getLeefgebiedRandomSampleGenerator() {
        return new Leefgebied().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
