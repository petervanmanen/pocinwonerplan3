package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WegdeelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Wegdeel getWegdeelSample1() {
        return new Wegdeel()
            .id(1L)
            .functiewegdeel("functiewegdeel1")
            .fysiekvoorkomenwegdeel("fysiekvoorkomenwegdeel1")
            .geometriewegdeel("geometriewegdeel1")
            .identificatiewegdeel("identificatiewegdeel1")
            .kruinlijngeometriewegdeel("kruinlijngeometriewegdeel1")
            .lod0geometriewegdeel("lod0geometriewegdeel1")
            .plusfunctiewegdeel("plusfunctiewegdeel1")
            .plusfysiekvoorkomenwegdeel("plusfysiekvoorkomenwegdeel1")
            .relatievehoogteliggingwegdeel("relatievehoogteliggingwegdeel1")
            .statuswegdeel("statuswegdeel1")
            .wegdeeloptalud("wegdeeloptalud1");
    }

    public static Wegdeel getWegdeelSample2() {
        return new Wegdeel()
            .id(2L)
            .functiewegdeel("functiewegdeel2")
            .fysiekvoorkomenwegdeel("fysiekvoorkomenwegdeel2")
            .geometriewegdeel("geometriewegdeel2")
            .identificatiewegdeel("identificatiewegdeel2")
            .kruinlijngeometriewegdeel("kruinlijngeometriewegdeel2")
            .lod0geometriewegdeel("lod0geometriewegdeel2")
            .plusfunctiewegdeel("plusfunctiewegdeel2")
            .plusfysiekvoorkomenwegdeel("plusfysiekvoorkomenwegdeel2")
            .relatievehoogteliggingwegdeel("relatievehoogteliggingwegdeel2")
            .statuswegdeel("statuswegdeel2")
            .wegdeeloptalud("wegdeeloptalud2");
    }

    public static Wegdeel getWegdeelRandomSampleGenerator() {
        return new Wegdeel()
            .id(longCount.incrementAndGet())
            .functiewegdeel(UUID.randomUUID().toString())
            .fysiekvoorkomenwegdeel(UUID.randomUUID().toString())
            .geometriewegdeel(UUID.randomUUID().toString())
            .identificatiewegdeel(UUID.randomUUID().toString())
            .kruinlijngeometriewegdeel(UUID.randomUUID().toString())
            .lod0geometriewegdeel(UUID.randomUUID().toString())
            .plusfunctiewegdeel(UUID.randomUUID().toString())
            .plusfysiekvoorkomenwegdeel(UUID.randomUUID().toString())
            .relatievehoogteliggingwegdeel(UUID.randomUUID().toString())
            .statuswegdeel(UUID.randomUUID().toString())
            .wegdeeloptalud(UUID.randomUUID().toString());
    }
}
