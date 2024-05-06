package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AdresseerbaarobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Adresseerbaarobject getAdresseerbaarobjectSample1() {
        return new Adresseerbaarobject()
            .id(1L)
            .identificatie("identificatie1")
            .typeadresseerbaarobject("typeadresseerbaarobject1")
            .versie("versie1");
    }

    public static Adresseerbaarobject getAdresseerbaarobjectSample2() {
        return new Adresseerbaarobject()
            .id(2L)
            .identificatie("identificatie2")
            .typeadresseerbaarobject("typeadresseerbaarobject2")
            .versie("versie2");
    }

    public static Adresseerbaarobject getAdresseerbaarobjectRandomSampleGenerator() {
        return new Adresseerbaarobject()
            .id(longCount.incrementAndGet())
            .identificatie(UUID.randomUUID().toString())
            .typeadresseerbaarobject(UUID.randomUUID().toString())
            .versie(UUID.randomUUID().toString());
    }
}
