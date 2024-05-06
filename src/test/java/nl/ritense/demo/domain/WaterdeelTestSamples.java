package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WaterdeelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Waterdeel getWaterdeelSample1() {
        return new Waterdeel()
            .id(1L)
            .geometriewaterdeel("geometriewaterdeel1")
            .identificatiewaterdeel("identificatiewaterdeel1")
            .plustypewaterdeel("plustypewaterdeel1")
            .relatievehoogteliggingwaterdeel("relatievehoogteliggingwaterdeel1")
            .statuswaterdeel("statuswaterdeel1")
            .typewaterdeel("typewaterdeel1");
    }

    public static Waterdeel getWaterdeelSample2() {
        return new Waterdeel()
            .id(2L)
            .geometriewaterdeel("geometriewaterdeel2")
            .identificatiewaterdeel("identificatiewaterdeel2")
            .plustypewaterdeel("plustypewaterdeel2")
            .relatievehoogteliggingwaterdeel("relatievehoogteliggingwaterdeel2")
            .statuswaterdeel("statuswaterdeel2")
            .typewaterdeel("typewaterdeel2");
    }

    public static Waterdeel getWaterdeelRandomSampleGenerator() {
        return new Waterdeel()
            .id(longCount.incrementAndGet())
            .geometriewaterdeel(UUID.randomUUID().toString())
            .identificatiewaterdeel(UUID.randomUUID().toString())
            .plustypewaterdeel(UUID.randomUUID().toString())
            .relatievehoogteliggingwaterdeel(UUID.randomUUID().toString())
            .statuswaterdeel(UUID.randomUUID().toString())
            .typewaterdeel(UUID.randomUUID().toString());
    }
}
