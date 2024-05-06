package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OverigescheidingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Overigescheiding getOverigescheidingSample1() {
        return new Overigescheiding()
            .id(1L)
            .geometrieoverigescheiding("geometrieoverigescheiding1")
            .identificatieoverigescheiding("identificatieoverigescheiding1")
            .lod0geometrieoverigescheiding("lod0geometrieoverigescheiding1")
            .lod1geometrieoverigescheiding("lod1geometrieoverigescheiding1")
            .lod2geometrieoverigescheiding("lod2geometrieoverigescheiding1")
            .lod3geometrieoverigescheiding("lod3geometrieoverigescheiding1")
            .relatievehoogteliggingoverigescheiding("relatievehoogteliggingoverigescheiding1")
            .statusoverigescheiding("statusoverigescheiding1")
            .typeoverigescheiding("typeoverigescheiding1");
    }

    public static Overigescheiding getOverigescheidingSample2() {
        return new Overigescheiding()
            .id(2L)
            .geometrieoverigescheiding("geometrieoverigescheiding2")
            .identificatieoverigescheiding("identificatieoverigescheiding2")
            .lod0geometrieoverigescheiding("lod0geometrieoverigescheiding2")
            .lod1geometrieoverigescheiding("lod1geometrieoverigescheiding2")
            .lod2geometrieoverigescheiding("lod2geometrieoverigescheiding2")
            .lod3geometrieoverigescheiding("lod3geometrieoverigescheiding2")
            .relatievehoogteliggingoverigescheiding("relatievehoogteliggingoverigescheiding2")
            .statusoverigescheiding("statusoverigescheiding2")
            .typeoverigescheiding("typeoverigescheiding2");
    }

    public static Overigescheiding getOverigescheidingRandomSampleGenerator() {
        return new Overigescheiding()
            .id(longCount.incrementAndGet())
            .geometrieoverigescheiding(UUID.randomUUID().toString())
            .identificatieoverigescheiding(UUID.randomUUID().toString())
            .lod0geometrieoverigescheiding(UUID.randomUUID().toString())
            .lod1geometrieoverigescheiding(UUID.randomUUID().toString())
            .lod2geometrieoverigescheiding(UUID.randomUUID().toString())
            .lod3geometrieoverigescheiding(UUID.randomUUID().toString())
            .relatievehoogteliggingoverigescheiding(UUID.randomUUID().toString())
            .statusoverigescheiding(UUID.randomUUID().toString())
            .typeoverigescheiding(UUID.randomUUID().toString());
    }
}
