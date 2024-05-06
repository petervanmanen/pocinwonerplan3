package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PeriodeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Periode getPeriodeSample1() {
        return new Periode().id(1L).datumeinde("datumeinde1").datumstart("datumstart1").omschrijving("omschrijving1");
    }

    public static Periode getPeriodeSample2() {
        return new Periode().id(2L).datumeinde("datumeinde2").datumstart("datumstart2").omschrijving("omschrijving2");
    }

    public static Periode getPeriodeRandomSampleGenerator() {
        return new Periode()
            .id(longCount.incrementAndGet())
            .datumeinde(UUID.randomUUID().toString())
            .datumstart(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
