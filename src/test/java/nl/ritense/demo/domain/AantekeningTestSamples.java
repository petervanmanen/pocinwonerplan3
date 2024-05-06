package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AantekeningTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aantekening getAantekeningSample1() {
        return new Aantekening()
            .id(1L)
            .aard("aard1")
            .begrenzing("begrenzing1")
            .identificatie("identificatie1")
            .omschrijving("omschrijving1");
    }

    public static Aantekening getAantekeningSample2() {
        return new Aantekening()
            .id(2L)
            .aard("aard2")
            .begrenzing("begrenzing2")
            .identificatie("identificatie2")
            .omschrijving("omschrijving2");
    }

    public static Aantekening getAantekeningRandomSampleGenerator() {
        return new Aantekening()
            .id(longCount.incrementAndGet())
            .aard(UUID.randomUUID().toString())
            .begrenzing(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
