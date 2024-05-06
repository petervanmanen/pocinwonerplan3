package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StremmingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Stremming getStremmingSample1() {
        return new Stremming()
            .id(1L)
            .aantalgehinderden("aantalgehinderden1")
            .datumaanmelding("datumaanmelding1")
            .datumeinde("datumeinde1")
            .datumstart("datumstart1")
            .datumwijziging("datumwijziging1")
            .hinderklasse("hinderklasse1")
            .locatie("locatie1")
            .naam("naam1")
            .status("status1");
    }

    public static Stremming getStremmingSample2() {
        return new Stremming()
            .id(2L)
            .aantalgehinderden("aantalgehinderden2")
            .datumaanmelding("datumaanmelding2")
            .datumeinde("datumeinde2")
            .datumstart("datumstart2")
            .datumwijziging("datumwijziging2")
            .hinderklasse("hinderklasse2")
            .locatie("locatie2")
            .naam("naam2")
            .status("status2");
    }

    public static Stremming getStremmingRandomSampleGenerator() {
        return new Stremming()
            .id(longCount.incrementAndGet())
            .aantalgehinderden(UUID.randomUUID().toString())
            .datumaanmelding(UUID.randomUUID().toString())
            .datumeinde(UUID.randomUUID().toString())
            .datumstart(UUID.randomUUID().toString())
            .datumwijziging(UUID.randomUUID().toString())
            .hinderklasse(UUID.randomUUID().toString())
            .locatie(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString());
    }
}
