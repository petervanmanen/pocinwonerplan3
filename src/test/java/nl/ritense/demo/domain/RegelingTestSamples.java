package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RegelingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Regeling getRegelingSample1() {
        return new Regeling()
            .id(1L)
            .datumeinde("datumeinde1")
            .datumstart("datumstart1")
            .datumtoekenning("datumtoekenning1")
            .omschrijving("omschrijving1");
    }

    public static Regeling getRegelingSample2() {
        return new Regeling()
            .id(2L)
            .datumeinde("datumeinde2")
            .datumstart("datumstart2")
            .datumtoekenning("datumtoekenning2")
            .omschrijving("omschrijving2");
    }

    public static Regeling getRegelingRandomSampleGenerator() {
        return new Regeling()
            .id(longCount.incrementAndGet())
            .datumeinde(UUID.randomUUID().toString())
            .datumstart(UUID.randomUUID().toString())
            .datumtoekenning(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
