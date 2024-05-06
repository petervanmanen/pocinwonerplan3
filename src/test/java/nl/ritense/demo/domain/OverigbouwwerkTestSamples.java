package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OverigbouwwerkTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Overigbouwwerk getOverigbouwwerkSample1() {
        return new Overigbouwwerk()
            .id(1L)
            .geometrieoverigbouwwerk("geometrieoverigbouwwerk1")
            .identificatieoverigbouwwerk("identificatieoverigbouwwerk1")
            .lod0geometrieoverigbouwwerk("lod0geometrieoverigbouwwerk1")
            .lod1geometrieoverigbouwwerk("lod1geometrieoverigbouwwerk1")
            .lod2geometrieoverigbouwwerk("lod2geometrieoverigbouwwerk1")
            .lod3geometrieoverigbouwwerk("lod3geometrieoverigbouwwerk1")
            .relatievehoogteliggingoverigbouwwerk("relatievehoogteliggingoverigbouwwerk1")
            .statusoverigbouwwerk("statusoverigbouwwerk1");
    }

    public static Overigbouwwerk getOverigbouwwerkSample2() {
        return new Overigbouwwerk()
            .id(2L)
            .geometrieoverigbouwwerk("geometrieoverigbouwwerk2")
            .identificatieoverigbouwwerk("identificatieoverigbouwwerk2")
            .lod0geometrieoverigbouwwerk("lod0geometrieoverigbouwwerk2")
            .lod1geometrieoverigbouwwerk("lod1geometrieoverigbouwwerk2")
            .lod2geometrieoverigbouwwerk("lod2geometrieoverigbouwwerk2")
            .lod3geometrieoverigbouwwerk("lod3geometrieoverigbouwwerk2")
            .relatievehoogteliggingoverigbouwwerk("relatievehoogteliggingoverigbouwwerk2")
            .statusoverigbouwwerk("statusoverigbouwwerk2");
    }

    public static Overigbouwwerk getOverigbouwwerkRandomSampleGenerator() {
        return new Overigbouwwerk()
            .id(longCount.incrementAndGet())
            .geometrieoverigbouwwerk(UUID.randomUUID().toString())
            .identificatieoverigbouwwerk(UUID.randomUUID().toString())
            .lod0geometrieoverigbouwwerk(UUID.randomUUID().toString())
            .lod1geometrieoverigbouwwerk(UUID.randomUUID().toString())
            .lod2geometrieoverigbouwwerk(UUID.randomUUID().toString())
            .lod3geometrieoverigbouwwerk(UUID.randomUUID().toString())
            .relatievehoogteliggingoverigbouwwerk(UUID.randomUUID().toString())
            .statusoverigbouwwerk(UUID.randomUUID().toString());
    }
}
