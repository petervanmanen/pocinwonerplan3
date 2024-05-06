package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BedrijfsprocestypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bedrijfsprocestype getBedrijfsprocestypeSample1() {
        return new Bedrijfsprocestype().id(1L).omschrijving("omschrijving1");
    }

    public static Bedrijfsprocestype getBedrijfsprocestypeSample2() {
        return new Bedrijfsprocestype().id(2L).omschrijving("omschrijving2");
    }

    public static Bedrijfsprocestype getBedrijfsprocestypeRandomSampleGenerator() {
        return new Bedrijfsprocestype().id(longCount.incrementAndGet()).omschrijving(UUID.randomUUID().toString());
    }
}
