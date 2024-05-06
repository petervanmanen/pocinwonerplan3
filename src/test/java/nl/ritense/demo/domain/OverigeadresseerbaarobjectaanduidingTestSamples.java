package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OverigeadresseerbaarobjectaanduidingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Overigeadresseerbaarobjectaanduiding getOverigeadresseerbaarobjectaanduidingSample1() {
        return new Overigeadresseerbaarobjectaanduiding()
            .id(1L)
            .identificatiecodeoverigadresseerbaarobjectaanduiding("identificatiecodeoverigadresseerbaarobjectaanduiding1");
    }

    public static Overigeadresseerbaarobjectaanduiding getOverigeadresseerbaarobjectaanduidingSample2() {
        return new Overigeadresseerbaarobjectaanduiding()
            .id(2L)
            .identificatiecodeoverigadresseerbaarobjectaanduiding("identificatiecodeoverigadresseerbaarobjectaanduiding2");
    }

    public static Overigeadresseerbaarobjectaanduiding getOverigeadresseerbaarobjectaanduidingRandomSampleGenerator() {
        return new Overigeadresseerbaarobjectaanduiding()
            .id(longCount.incrementAndGet())
            .identificatiecodeoverigadresseerbaarobjectaanduiding(UUID.randomUUID().toString());
    }
}
