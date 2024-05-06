package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StuwgebiedTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Stuwgebied getStuwgebiedSample1() {
        return new Stuwgebied().id(1L).bemalingsgebied("bemalingsgebied1");
    }

    public static Stuwgebied getStuwgebiedSample2() {
        return new Stuwgebied().id(2L).bemalingsgebied("bemalingsgebied2");
    }

    public static Stuwgebied getStuwgebiedRandomSampleGenerator() {
        return new Stuwgebied().id(longCount.incrementAndGet()).bemalingsgebied(UUID.randomUUID().toString());
    }
}
