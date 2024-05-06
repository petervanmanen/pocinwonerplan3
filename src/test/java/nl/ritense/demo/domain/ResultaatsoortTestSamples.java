package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ResultaatsoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Resultaatsoort getResultaatsoortSample1() {
        return new Resultaatsoort().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Resultaatsoort getResultaatsoortSample2() {
        return new Resultaatsoort().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Resultaatsoort getResultaatsoortRandomSampleGenerator() {
        return new Resultaatsoort()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
