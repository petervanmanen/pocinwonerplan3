package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SociaalteamdossiersoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sociaalteamdossiersoort getSociaalteamdossiersoortSample1() {
        return new Sociaalteamdossiersoort().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Sociaalteamdossiersoort getSociaalteamdossiersoortSample2() {
        return new Sociaalteamdossiersoort().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Sociaalteamdossiersoort getSociaalteamdossiersoortRandomSampleGenerator() {
        return new Sociaalteamdossiersoort()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
