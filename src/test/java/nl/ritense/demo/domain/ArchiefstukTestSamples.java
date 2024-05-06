package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArchiefstukTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Archiefstuk getArchiefstukSample1() {
        return new Archiefstuk()
            .id(1L)
            .beschrijving("beschrijving1")
            .inventarisnummer("inventarisnummer1")
            .omvang("omvang1")
            .openbaarheidsbeperking("openbaarheidsbeperking1")
            .trefwoorden("trefwoorden1")
            .uiterlijkevorm("uiterlijkevorm1");
    }

    public static Archiefstuk getArchiefstukSample2() {
        return new Archiefstuk()
            .id(2L)
            .beschrijving("beschrijving2")
            .inventarisnummer("inventarisnummer2")
            .omvang("omvang2")
            .openbaarheidsbeperking("openbaarheidsbeperking2")
            .trefwoorden("trefwoorden2")
            .uiterlijkevorm("uiterlijkevorm2");
    }

    public static Archiefstuk getArchiefstukRandomSampleGenerator() {
        return new Archiefstuk()
            .id(longCount.incrementAndGet())
            .beschrijving(UUID.randomUUID().toString())
            .inventarisnummer(UUID.randomUUID().toString())
            .omvang(UUID.randomUUID().toString())
            .openbaarheidsbeperking(UUID.randomUUID().toString())
            .trefwoorden(UUID.randomUUID().toString())
            .uiterlijkevorm(UUID.randomUUID().toString());
    }
}
