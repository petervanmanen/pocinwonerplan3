package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TunneldeelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Tunneldeel getTunneldeelSample1() {
        return new Tunneldeel()
            .id(1L)
            .geometrietunneldeel("geometrietunneldeel1")
            .identificatietunneldeel("identificatietunneldeel1")
            .lod0geometrietunneldeel("lod0geometrietunneldeel1")
            .relatievehoogteliggingtunneldeel("relatievehoogteliggingtunneldeel1")
            .statustunneldeel("statustunneldeel1");
    }

    public static Tunneldeel getTunneldeelSample2() {
        return new Tunneldeel()
            .id(2L)
            .geometrietunneldeel("geometrietunneldeel2")
            .identificatietunneldeel("identificatietunneldeel2")
            .lod0geometrietunneldeel("lod0geometrietunneldeel2")
            .relatievehoogteliggingtunneldeel("relatievehoogteliggingtunneldeel2")
            .statustunneldeel("statustunneldeel2");
    }

    public static Tunneldeel getTunneldeelRandomSampleGenerator() {
        return new Tunneldeel()
            .id(longCount.incrementAndGet())
            .geometrietunneldeel(UUID.randomUUID().toString())
            .identificatietunneldeel(UUID.randomUUID().toString())
            .lod0geometrietunneldeel(UUID.randomUUID().toString())
            .relatievehoogteliggingtunneldeel(UUID.randomUUID().toString())
            .statustunneldeel(UUID.randomUUID().toString());
    }
}
