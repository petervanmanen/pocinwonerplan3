package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LandofgebiedTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Landofgebied getLandofgebiedSample1() {
        return new Landofgebied().id(1L).landcode("landcode1").landcodeiso("landcodeiso1").landnaam("landnaam1");
    }

    public static Landofgebied getLandofgebiedSample2() {
        return new Landofgebied().id(2L).landcode("landcode2").landcodeiso("landcodeiso2").landnaam("landnaam2");
    }

    public static Landofgebied getLandofgebiedRandomSampleGenerator() {
        return new Landofgebied()
            .id(longCount.incrementAndGet())
            .landcode(UUID.randomUUID().toString())
            .landcodeiso(UUID.randomUUID().toString())
            .landnaam(UUID.randomUUID().toString());
    }
}
