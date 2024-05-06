package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AnderzaakobjectzaakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Anderzaakobjectzaak getAnderzaakobjectzaakSample1() {
        return new Anderzaakobjectzaak()
            .id(1L)
            .anderzaakobjectaanduiding("anderzaakobjectaanduiding1")
            .anderzaakobjectlocatie("anderzaakobjectlocatie1")
            .anderzaakobjectomschrijving("anderzaakobjectomschrijving1")
            .anderzaakobjectregistratie("anderzaakobjectregistratie1");
    }

    public static Anderzaakobjectzaak getAnderzaakobjectzaakSample2() {
        return new Anderzaakobjectzaak()
            .id(2L)
            .anderzaakobjectaanduiding("anderzaakobjectaanduiding2")
            .anderzaakobjectlocatie("anderzaakobjectlocatie2")
            .anderzaakobjectomschrijving("anderzaakobjectomschrijving2")
            .anderzaakobjectregistratie("anderzaakobjectregistratie2");
    }

    public static Anderzaakobjectzaak getAnderzaakobjectzaakRandomSampleGenerator() {
        return new Anderzaakobjectzaak()
            .id(longCount.incrementAndGet())
            .anderzaakobjectaanduiding(UUID.randomUUID().toString())
            .anderzaakobjectlocatie(UUID.randomUUID().toString())
            .anderzaakobjectomschrijving(UUID.randomUUID().toString())
            .anderzaakobjectregistratie(UUID.randomUUID().toString());
    }
}
