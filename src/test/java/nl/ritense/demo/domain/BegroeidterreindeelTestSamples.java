package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BegroeidterreindeelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Begroeidterreindeel getBegroeidterreindeelSample1() {
        return new Begroeidterreindeel()
            .id(1L)
            .begroeidterreindeeloptalud("begroeidterreindeeloptalud1")
            .fysiekvoorkomenbegroeidterreindeel("fysiekvoorkomenbegroeidterreindeel1")
            .geometriebegroeidterreindeel("geometriebegroeidterreindeel1")
            .identificatiebegroeidterreindeel("identificatiebegroeidterreindeel1")
            .kruinlijngeometriebegroeidterreindeel("kruinlijngeometriebegroeidterreindeel1")
            .lod0geometriebegroeidterreindeel("lod0geometriebegroeidterreindeel1")
            .plusfysiekvoorkomenbegroeidterreindeel("plusfysiekvoorkomenbegroeidterreindeel1")
            .relatievehoogteliggingbegroeidterreindeel("relatievehoogteliggingbegroeidterreindeel1")
            .statusbegroeidterreindeel("statusbegroeidterreindeel1");
    }

    public static Begroeidterreindeel getBegroeidterreindeelSample2() {
        return new Begroeidterreindeel()
            .id(2L)
            .begroeidterreindeeloptalud("begroeidterreindeeloptalud2")
            .fysiekvoorkomenbegroeidterreindeel("fysiekvoorkomenbegroeidterreindeel2")
            .geometriebegroeidterreindeel("geometriebegroeidterreindeel2")
            .identificatiebegroeidterreindeel("identificatiebegroeidterreindeel2")
            .kruinlijngeometriebegroeidterreindeel("kruinlijngeometriebegroeidterreindeel2")
            .lod0geometriebegroeidterreindeel("lod0geometriebegroeidterreindeel2")
            .plusfysiekvoorkomenbegroeidterreindeel("plusfysiekvoorkomenbegroeidterreindeel2")
            .relatievehoogteliggingbegroeidterreindeel("relatievehoogteliggingbegroeidterreindeel2")
            .statusbegroeidterreindeel("statusbegroeidterreindeel2");
    }

    public static Begroeidterreindeel getBegroeidterreindeelRandomSampleGenerator() {
        return new Begroeidterreindeel()
            .id(longCount.incrementAndGet())
            .begroeidterreindeeloptalud(UUID.randomUUID().toString())
            .fysiekvoorkomenbegroeidterreindeel(UUID.randomUUID().toString())
            .geometriebegroeidterreindeel(UUID.randomUUID().toString())
            .identificatiebegroeidterreindeel(UUID.randomUUID().toString())
            .kruinlijngeometriebegroeidterreindeel(UUID.randomUUID().toString())
            .lod0geometriebegroeidterreindeel(UUID.randomUUID().toString())
            .plusfysiekvoorkomenbegroeidterreindeel(UUID.randomUUID().toString())
            .relatievehoogteliggingbegroeidterreindeel(UUID.randomUUID().toString())
            .statusbegroeidterreindeel(UUID.randomUUID().toString());
    }
}
