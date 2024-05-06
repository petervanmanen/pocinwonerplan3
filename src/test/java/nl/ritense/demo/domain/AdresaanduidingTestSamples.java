package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AdresaanduidingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Adresaanduiding getAdresaanduidingSample1() {
        return new Adresaanduiding().id(1L).adresaanduiding("adresaanduiding1");
    }

    public static Adresaanduiding getAdresaanduidingSample2() {
        return new Adresaanduiding().id(2L).adresaanduiding("adresaanduiding2");
    }

    public static Adresaanduiding getAdresaanduidingRandomSampleGenerator() {
        return new Adresaanduiding().id(longCount.incrementAndGet()).adresaanduiding(UUID.randomUUID().toString());
    }
}
