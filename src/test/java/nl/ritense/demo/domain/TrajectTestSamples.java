package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TrajectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Traject getTrajectSample1() {
        return new Traject()
            .id(1L)
            .datumeinde("datumeinde1")
            .datumstart("datumstart1")
            .datumtoekenning("datumtoekenning1")
            .naam("naam1")
            .omschrijving("omschrijving1")
            .resultaat("resultaat1");
    }

    public static Traject getTrajectSample2() {
        return new Traject()
            .id(2L)
            .datumeinde("datumeinde2")
            .datumstart("datumstart2")
            .datumtoekenning("datumtoekenning2")
            .naam("naam2")
            .omschrijving("omschrijving2")
            .resultaat("resultaat2");
    }

    public static Traject getTrajectRandomSampleGenerator() {
        return new Traject()
            .id(longCount.incrementAndGet())
            .datumeinde(UUID.randomUUID().toString())
            .datumstart(UUID.randomUUID().toString())
            .datumtoekenning(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .resultaat(UUID.randomUUID().toString());
    }
}
