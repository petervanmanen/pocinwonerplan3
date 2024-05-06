package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RioleringsgebiedTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Rioleringsgebied getRioleringsgebiedSample1() {
        return new Rioleringsgebied().id(1L).rioleringsgebied("rioleringsgebied1").zuiveringsgebied("zuiveringsgebied1");
    }

    public static Rioleringsgebied getRioleringsgebiedSample2() {
        return new Rioleringsgebied().id(2L).rioleringsgebied("rioleringsgebied2").zuiveringsgebied("zuiveringsgebied2");
    }

    public static Rioleringsgebied getRioleringsgebiedRandomSampleGenerator() {
        return new Rioleringsgebied()
            .id(longCount.incrementAndGet())
            .rioleringsgebied(UUID.randomUUID().toString())
            .zuiveringsgebied(UUID.randomUUID().toString());
    }
}
