package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DossierTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Dossier getDossierSample1() {
        return new Dossier().id(1L).naam("naam1");
    }

    public static Dossier getDossierSample2() {
        return new Dossier().id(2L).naam("naam2");
    }

    public static Dossier getDossierRandomSampleGenerator() {
        return new Dossier().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
