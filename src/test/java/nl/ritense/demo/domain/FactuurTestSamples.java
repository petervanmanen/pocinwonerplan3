package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FactuurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Factuur getFactuurSample1() {
        return new Factuur()
            .id(1L)
            .betaalbaarper("betaalbaarper1")
            .betaaltermijn("betaaltermijn1")
            .code("code1")
            .datumfactuur("datumfactuur1")
            .factuurbedragexclusiefbtw("factuurbedragexclusiefbtw1")
            .omschrijving("omschrijving1");
    }

    public static Factuur getFactuurSample2() {
        return new Factuur()
            .id(2L)
            .betaalbaarper("betaalbaarper2")
            .betaaltermijn("betaaltermijn2")
            .code("code2")
            .datumfactuur("datumfactuur2")
            .factuurbedragexclusiefbtw("factuurbedragexclusiefbtw2")
            .omschrijving("omschrijving2");
    }

    public static Factuur getFactuurRandomSampleGenerator() {
        return new Factuur()
            .id(longCount.incrementAndGet())
            .betaalbaarper(UUID.randomUUID().toString())
            .betaaltermijn(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .datumfactuur(UUID.randomUUID().toString())
            .factuurbedragexclusiefbtw(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
