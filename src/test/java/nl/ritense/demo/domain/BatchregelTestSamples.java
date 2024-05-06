package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BatchregelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Batchregel getBatchregelSample1() {
        return new Batchregel().id(1L).omschrijving("omschrijving1").rekeningnaar("rekeningnaar1").rekeningvan("rekeningvan1");
    }

    public static Batchregel getBatchregelSample2() {
        return new Batchregel().id(2L).omschrijving("omschrijving2").rekeningnaar("rekeningnaar2").rekeningvan("rekeningvan2");
    }

    public static Batchregel getBatchregelRandomSampleGenerator() {
        return new Batchregel()
            .id(longCount.incrementAndGet())
            .omschrijving(UUID.randomUUID().toString())
            .rekeningnaar(UUID.randomUUID().toString())
            .rekeningvan(UUID.randomUUID().toString());
    }
}
