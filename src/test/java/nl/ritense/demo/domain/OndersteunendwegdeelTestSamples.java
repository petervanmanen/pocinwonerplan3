package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OndersteunendwegdeelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ondersteunendwegdeel getOndersteunendwegdeelSample1() {
        return new Ondersteunendwegdeel()
            .id(1L)
            .functieondersteunendwegdeel("functieondersteunendwegdeel1")
            .fysiekvoorkomenondersteunendwegdeel("fysiekvoorkomenondersteunendwegdeel1")
            .geometrieondersteunendwegdeel("geometrieondersteunendwegdeel1")
            .identificatieondersteunendwegdeel("identificatieondersteunendwegdeel1")
            .kruinlijngeometrieondersteunendwegdeel("kruinlijngeometrieondersteunendwegdeel1")
            .lod0geometrieondersteunendwegdeel("lod0geometrieondersteunendwegdeel1")
            .ondersteunendwegdeeloptalud("ondersteunendwegdeeloptalud1")
            .plusfunctieondersteunendwegdeel("plusfunctieondersteunendwegdeel1")
            .plusfysiekvoorkomenondersteunendwegdeel("plusfysiekvoorkomenondersteunendwegdeel1")
            .relatievehoogteliggingondersteunendwegdeel("relatievehoogteliggingondersteunendwegdeel1")
            .statusondersteunendwegdeel("statusondersteunendwegdeel1");
    }

    public static Ondersteunendwegdeel getOndersteunendwegdeelSample2() {
        return new Ondersteunendwegdeel()
            .id(2L)
            .functieondersteunendwegdeel("functieondersteunendwegdeel2")
            .fysiekvoorkomenondersteunendwegdeel("fysiekvoorkomenondersteunendwegdeel2")
            .geometrieondersteunendwegdeel("geometrieondersteunendwegdeel2")
            .identificatieondersteunendwegdeel("identificatieondersteunendwegdeel2")
            .kruinlijngeometrieondersteunendwegdeel("kruinlijngeometrieondersteunendwegdeel2")
            .lod0geometrieondersteunendwegdeel("lod0geometrieondersteunendwegdeel2")
            .ondersteunendwegdeeloptalud("ondersteunendwegdeeloptalud2")
            .plusfunctieondersteunendwegdeel("plusfunctieondersteunendwegdeel2")
            .plusfysiekvoorkomenondersteunendwegdeel("plusfysiekvoorkomenondersteunendwegdeel2")
            .relatievehoogteliggingondersteunendwegdeel("relatievehoogteliggingondersteunendwegdeel2")
            .statusondersteunendwegdeel("statusondersteunendwegdeel2");
    }

    public static Ondersteunendwegdeel getOndersteunendwegdeelRandomSampleGenerator() {
        return new Ondersteunendwegdeel()
            .id(longCount.incrementAndGet())
            .functieondersteunendwegdeel(UUID.randomUUID().toString())
            .fysiekvoorkomenondersteunendwegdeel(UUID.randomUUID().toString())
            .geometrieondersteunendwegdeel(UUID.randomUUID().toString())
            .identificatieondersteunendwegdeel(UUID.randomUUID().toString())
            .kruinlijngeometrieondersteunendwegdeel(UUID.randomUUID().toString())
            .lod0geometrieondersteunendwegdeel(UUID.randomUUID().toString())
            .ondersteunendwegdeeloptalud(UUID.randomUUID().toString())
            .plusfunctieondersteunendwegdeel(UUID.randomUUID().toString())
            .plusfysiekvoorkomenondersteunendwegdeel(UUID.randomUUID().toString())
            .relatievehoogteliggingondersteunendwegdeel(UUID.randomUUID().toString())
            .statusondersteunendwegdeel(UUID.randomUUID().toString());
    }
}
