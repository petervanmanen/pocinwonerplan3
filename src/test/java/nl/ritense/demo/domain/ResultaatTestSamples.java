package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ResultaatTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Resultaat getResultaatSample1() {
        return new Resultaat().id(1L).datum("datum1").naam("naam1").omschrijving("omschrijving1");
    }

    public static Resultaat getResultaatSample2() {
        return new Resultaat().id(2L).datum("datum2").naam("naam2").omschrijving("omschrijving2");
    }

    public static Resultaat getResultaatRandomSampleGenerator() {
        return new Resultaat()
            .id(longCount.incrementAndGet())
            .datum(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
