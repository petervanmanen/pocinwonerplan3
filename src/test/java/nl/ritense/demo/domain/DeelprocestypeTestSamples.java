package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DeelprocestypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Deelprocestype getDeelprocestypeSample1() {
        return new Deelprocestype().id(1L).omschrijving("omschrijving1");
    }

    public static Deelprocestype getDeelprocestypeSample2() {
        return new Deelprocestype().id(2L).omschrijving("omschrijving2");
    }

    public static Deelprocestype getDeelprocestypeRandomSampleGenerator() {
        return new Deelprocestype().id(longCount.incrementAndGet()).omschrijving(UUID.randomUUID().toString());
    }
}
