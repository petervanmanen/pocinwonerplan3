package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OverbruggingsdeelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Overbruggingsdeel getOverbruggingsdeelSample1() {
        return new Overbruggingsdeel()
            .id(1L)
            .geometrieoverbruggingsdeel("geometrieoverbruggingsdeel1")
            .hoortbijtypeoverbrugging("hoortbijtypeoverbrugging1")
            .identificatieoverbruggingsdeel("identificatieoverbruggingsdeel1")
            .lod0geometrieoverbruggingsdeel("lod0geometrieoverbruggingsdeel1")
            .overbruggingisbeweegbaar("overbruggingisbeweegbaar1")
            .relatievehoogteliggingoverbruggingsdeel("relatievehoogteliggingoverbruggingsdeel1")
            .statusoverbruggingsdeel("statusoverbruggingsdeel1")
            .typeoverbruggingsdeel("typeoverbruggingsdeel1");
    }

    public static Overbruggingsdeel getOverbruggingsdeelSample2() {
        return new Overbruggingsdeel()
            .id(2L)
            .geometrieoverbruggingsdeel("geometrieoverbruggingsdeel2")
            .hoortbijtypeoverbrugging("hoortbijtypeoverbrugging2")
            .identificatieoverbruggingsdeel("identificatieoverbruggingsdeel2")
            .lod0geometrieoverbruggingsdeel("lod0geometrieoverbruggingsdeel2")
            .overbruggingisbeweegbaar("overbruggingisbeweegbaar2")
            .relatievehoogteliggingoverbruggingsdeel("relatievehoogteliggingoverbruggingsdeel2")
            .statusoverbruggingsdeel("statusoverbruggingsdeel2")
            .typeoverbruggingsdeel("typeoverbruggingsdeel2");
    }

    public static Overbruggingsdeel getOverbruggingsdeelRandomSampleGenerator() {
        return new Overbruggingsdeel()
            .id(longCount.incrementAndGet())
            .geometrieoverbruggingsdeel(UUID.randomUUID().toString())
            .hoortbijtypeoverbrugging(UUID.randomUUID().toString())
            .identificatieoverbruggingsdeel(UUID.randomUUID().toString())
            .lod0geometrieoverbruggingsdeel(UUID.randomUUID().toString())
            .overbruggingisbeweegbaar(UUID.randomUUID().toString())
            .relatievehoogteliggingoverbruggingsdeel(UUID.randomUUID().toString())
            .statusoverbruggingsdeel(UUID.randomUUID().toString())
            .typeoverbruggingsdeel(UUID.randomUUID().toString());
    }
}
