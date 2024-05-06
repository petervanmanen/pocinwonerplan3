package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MeldingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Melding getMeldingSample1() {
        return new Melding()
            .id(1L)
            .vierentwintiguurs("vierentwintiguurs1")
            .datumtijd("datumtijd1")
            .illegaal("illegaal1")
            .meldingnummer("meldingnummer1")
            .omschrijving("omschrijving1");
    }

    public static Melding getMeldingSample2() {
        return new Melding()
            .id(2L)
            .vierentwintiguurs("vierentwintiguurs2")
            .datumtijd("datumtijd2")
            .illegaal("illegaal2")
            .meldingnummer("meldingnummer2")
            .omschrijving("omschrijving2");
    }

    public static Melding getMeldingRandomSampleGenerator() {
        return new Melding()
            .id(longCount.incrementAndGet())
            .vierentwintiguurs(UUID.randomUUID().toString())
            .datumtijd(UUID.randomUUID().toString())
            .illegaal(UUID.randomUUID().toString())
            .meldingnummer(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
