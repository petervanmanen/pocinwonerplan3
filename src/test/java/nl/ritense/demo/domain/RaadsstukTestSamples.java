package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RaadsstukTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Raadsstuk getRaadsstukSample1() {
        return new Raadsstuk()
            .id(1L)
            .besloten("besloten1")
            .datumexpiratie("datumexpiratie1")
            .datumpublicatie("datumpublicatie1")
            .datumregistratie("datumregistratie1")
            .typeraadsstuk("typeraadsstuk1");
    }

    public static Raadsstuk getRaadsstukSample2() {
        return new Raadsstuk()
            .id(2L)
            .besloten("besloten2")
            .datumexpiratie("datumexpiratie2")
            .datumpublicatie("datumpublicatie2")
            .datumregistratie("datumregistratie2")
            .typeraadsstuk("typeraadsstuk2");
    }

    public static Raadsstuk getRaadsstukRandomSampleGenerator() {
        return new Raadsstuk()
            .id(longCount.incrementAndGet())
            .besloten(UUID.randomUUID().toString())
            .datumexpiratie(UUID.randomUUID().toString())
            .datumpublicatie(UUID.randomUUID().toString())
            .datumregistratie(UUID.randomUUID().toString())
            .typeraadsstuk(UUID.randomUUID().toString());
    }
}
