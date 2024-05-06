package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerkeersbesluitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verkeersbesluit getVerkeersbesluitSample1() {
        return new Verkeersbesluit()
            .id(1L)
            .datumeinde("datumeinde1")
            .datumstart("datumstart1")
            .huisnummer("huisnummer1")
            .postcode("postcode1")
            .referentienummer("referentienummer1")
            .straat("straat1")
            .titel("titel1");
    }

    public static Verkeersbesluit getVerkeersbesluitSample2() {
        return new Verkeersbesluit()
            .id(2L)
            .datumeinde("datumeinde2")
            .datumstart("datumstart2")
            .huisnummer("huisnummer2")
            .postcode("postcode2")
            .referentienummer("referentienummer2")
            .straat("straat2")
            .titel("titel2");
    }

    public static Verkeersbesluit getVerkeersbesluitRandomSampleGenerator() {
        return new Verkeersbesluit()
            .id(longCount.incrementAndGet())
            .datumeinde(UUID.randomUUID().toString())
            .datumstart(UUID.randomUUID().toString())
            .huisnummer(UUID.randomUUID().toString())
            .postcode(UUID.randomUUID().toString())
            .referentienummer(UUID.randomUUID().toString())
            .straat(UUID.randomUUID().toString())
            .titel(UUID.randomUUID().toString());
    }
}
