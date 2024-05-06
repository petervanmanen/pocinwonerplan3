package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InkomensvoorzieningTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Inkomensvoorziening getInkomensvoorzieningSample1() {
        return new Inkomensvoorziening()
            .id(1L)
            .bedrag("bedrag1")
            .datumeinde("datumeinde1")
            .datumstart("datumstart1")
            .datumtoekenning("datumtoekenning1")
            .groep("groep1");
    }

    public static Inkomensvoorziening getInkomensvoorzieningSample2() {
        return new Inkomensvoorziening()
            .id(2L)
            .bedrag("bedrag2")
            .datumeinde("datumeinde2")
            .datumstart("datumstart2")
            .datumtoekenning("datumtoekenning2")
            .groep("groep2");
    }

    public static Inkomensvoorziening getInkomensvoorzieningRandomSampleGenerator() {
        return new Inkomensvoorziening()
            .id(longCount.incrementAndGet())
            .bedrag(UUID.randomUUID().toString())
            .datumeinde(UUID.randomUUID().toString())
            .datumstart(UUID.randomUUID().toString())
            .datumtoekenning(UUID.randomUUID().toString())
            .groep(UUID.randomUUID().toString());
    }
}
