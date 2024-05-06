package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SubsidieprogrammaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Subsidieprogramma getSubsidieprogrammaSample1() {
        return new Subsidieprogramma().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Subsidieprogramma getSubsidieprogrammaSample2() {
        return new Subsidieprogramma().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Subsidieprogramma getSubsidieprogrammaRandomSampleGenerator() {
        return new Subsidieprogramma()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
