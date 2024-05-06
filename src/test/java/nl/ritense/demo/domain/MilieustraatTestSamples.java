package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MilieustraatTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Milieustraat getMilieustraatSample1() {
        return new Milieustraat().id(1L).adresaanduiding("adresaanduiding1").naam("naam1").omschrijving("omschrijving1");
    }

    public static Milieustraat getMilieustraatSample2() {
        return new Milieustraat().id(2L).adresaanduiding("adresaanduiding2").naam("naam2").omschrijving("omschrijving2");
    }

    public static Milieustraat getMilieustraatRandomSampleGenerator() {
        return new Milieustraat()
            .id(longCount.incrementAndGet())
            .adresaanduiding(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
