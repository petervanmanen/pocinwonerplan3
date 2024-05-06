package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SociaalteamdossierTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sociaalteamdossier getSociaalteamdossierSample1() {
        return new Sociaalteamdossier()
            .id(1L)
            .datumeinde("datumeinde1")
            .datumstart("datumstart1")
            .datumvaststelling("datumvaststelling1")
            .omschrijving("omschrijving1")
            .status("status1");
    }

    public static Sociaalteamdossier getSociaalteamdossierSample2() {
        return new Sociaalteamdossier()
            .id(2L)
            .datumeinde("datumeinde2")
            .datumstart("datumstart2")
            .datumvaststelling("datumvaststelling2")
            .omschrijving("omschrijving2")
            .status("status2");
    }

    public static Sociaalteamdossier getSociaalteamdossierRandomSampleGenerator() {
        return new Sociaalteamdossier()
            .id(longCount.incrementAndGet())
            .datumeinde(UUID.randomUUID().toString())
            .datumstart(UUID.randomUUID().toString())
            .datumvaststelling(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString());
    }
}
