package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AdresseerbaarobjectaanduidingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Adresseerbaarobjectaanduiding getAdresseerbaarobjectaanduidingSample1() {
        return new Adresseerbaarobjectaanduiding().id(1L).identificatie("identificatie1");
    }

    public static Adresseerbaarobjectaanduiding getAdresseerbaarobjectaanduidingSample2() {
        return new Adresseerbaarobjectaanduiding().id(2L).identificatie("identificatie2");
    }

    public static Adresseerbaarobjectaanduiding getAdresseerbaarobjectaanduidingRandomSampleGenerator() {
        return new Adresseerbaarobjectaanduiding().id(longCount.incrementAndGet()).identificatie(UUID.randomUUID().toString());
    }
}
