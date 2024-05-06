package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BehandelingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Behandeling getBehandelingSample1() {
        return new Behandeling().id(1L).datumeinde("datumeinde1").datumstart("datumstart1").toelichting("toelichting1");
    }

    public static Behandeling getBehandelingSample2() {
        return new Behandeling().id(2L).datumeinde("datumeinde2").datumstart("datumstart2").toelichting("toelichting2");
    }

    public static Behandeling getBehandelingRandomSampleGenerator() {
        return new Behandeling()
            .id(longCount.incrementAndGet())
            .datumeinde(UUID.randomUUID().toString())
            .datumstart(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString());
    }
}
