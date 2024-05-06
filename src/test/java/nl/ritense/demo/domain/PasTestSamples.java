package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PasTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Pas getPasSample1() {
        return new Pas().id(1L).adresaanduiding("adresaanduiding1").pasnummer("pasnummer1");
    }

    public static Pas getPasSample2() {
        return new Pas().id(2L).adresaanduiding("adresaanduiding2").pasnummer("pasnummer2");
    }

    public static Pas getPasRandomSampleGenerator() {
        return new Pas()
            .id(longCount.incrementAndGet())
            .adresaanduiding(UUID.randomUUID().toString())
            .pasnummer(UUID.randomUUID().toString());
    }
}
