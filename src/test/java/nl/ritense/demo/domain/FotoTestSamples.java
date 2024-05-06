package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FotoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Foto getFotoSample1() {
        return new Foto()
            .id(1L)
            .bestandsgrootte("bestandsgrootte1")
            .bestandsnaam("bestandsnaam1")
            .bestandstype("bestandstype1")
            .datumtijd("datumtijd1")
            .locatie("locatie1")
            .pixelsx("pixelsx1")
            .pixelsy("pixelsy1");
    }

    public static Foto getFotoSample2() {
        return new Foto()
            .id(2L)
            .bestandsgrootte("bestandsgrootte2")
            .bestandsnaam("bestandsnaam2")
            .bestandstype("bestandstype2")
            .datumtijd("datumtijd2")
            .locatie("locatie2")
            .pixelsx("pixelsx2")
            .pixelsy("pixelsy2");
    }

    public static Foto getFotoRandomSampleGenerator() {
        return new Foto()
            .id(longCount.incrementAndGet())
            .bestandsgrootte(UUID.randomUUID().toString())
            .bestandsnaam(UUID.randomUUID().toString())
            .bestandstype(UUID.randomUUID().toString())
            .datumtijd(UUID.randomUUID().toString())
            .locatie(UUID.randomUUID().toString())
            .pixelsx(UUID.randomUUID().toString())
            .pixelsy(UUID.randomUUID().toString());
    }
}
