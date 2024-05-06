package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BehandelsoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Behandelsoort getBehandelsoortSample1() {
        return new Behandelsoort().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Behandelsoort getBehandelsoortSample2() {
        return new Behandelsoort().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Behandelsoort getBehandelsoortRandomSampleGenerator() {
        return new Behandelsoort()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
