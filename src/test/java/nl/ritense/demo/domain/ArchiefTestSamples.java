package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArchiefTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Archief getArchiefSample1() {
        return new Archief()
            .id(1L)
            .archiefnummer("archiefnummer1")
            .naam("naam1")
            .omschrijving("omschrijving1")
            .openbaarheidsbeperking("openbaarheidsbeperking1");
    }

    public static Archief getArchiefSample2() {
        return new Archief()
            .id(2L)
            .archiefnummer("archiefnummer2")
            .naam("naam2")
            .omschrijving("omschrijving2")
            .openbaarheidsbeperking("openbaarheidsbeperking2");
    }

    public static Archief getArchiefRandomSampleGenerator() {
        return new Archief()
            .id(longCount.incrementAndGet())
            .archiefnummer(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .openbaarheidsbeperking(UUID.randomUUID().toString());
    }
}
