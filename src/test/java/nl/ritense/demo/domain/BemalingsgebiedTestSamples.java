package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BemalingsgebiedTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bemalingsgebied getBemalingsgebiedSample1() {
        return new Bemalingsgebied().id(1L).rioleringsgebied("rioleringsgebied1");
    }

    public static Bemalingsgebied getBemalingsgebiedSample2() {
        return new Bemalingsgebied().id(2L).rioleringsgebied("rioleringsgebied2");
    }

    public static Bemalingsgebied getBemalingsgebiedRandomSampleGenerator() {
        return new Bemalingsgebied().id(longCount.incrementAndGet()).rioleringsgebied(UUID.randomUUID().toString());
    }
}
