package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BedrijfsprocesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bedrijfsproces getBedrijfsprocesSample1() {
        return new Bedrijfsproces().id(1L).afgerond("afgerond1").naam("naam1").omschrijving("omschrijving1");
    }

    public static Bedrijfsproces getBedrijfsprocesSample2() {
        return new Bedrijfsproces().id(2L).afgerond("afgerond2").naam("naam2").omschrijving("omschrijving2");
    }

    public static Bedrijfsproces getBedrijfsprocesRandomSampleGenerator() {
        return new Bedrijfsproces()
            .id(longCount.incrementAndGet())
            .afgerond(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
