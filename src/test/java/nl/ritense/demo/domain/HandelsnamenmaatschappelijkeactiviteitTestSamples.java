package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HandelsnamenmaatschappelijkeactiviteitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Handelsnamenmaatschappelijkeactiviteit getHandelsnamenmaatschappelijkeactiviteitSample1() {
        return new Handelsnamenmaatschappelijkeactiviteit()
            .id(1L)
            .handelsnaam("handelsnaam1")
            .verkortenaam("verkortenaam1")
            .volgorde("volgorde1");
    }

    public static Handelsnamenmaatschappelijkeactiviteit getHandelsnamenmaatschappelijkeactiviteitSample2() {
        return new Handelsnamenmaatschappelijkeactiviteit()
            .id(2L)
            .handelsnaam("handelsnaam2")
            .verkortenaam("verkortenaam2")
            .volgorde("volgorde2");
    }

    public static Handelsnamenmaatschappelijkeactiviteit getHandelsnamenmaatschappelijkeactiviteitRandomSampleGenerator() {
        return new Handelsnamenmaatschappelijkeactiviteit()
            .id(longCount.incrementAndGet())
            .handelsnaam(UUID.randomUUID().toString())
            .verkortenaam(UUID.randomUUID().toString())
            .volgorde(UUID.randomUUID().toString());
    }
}
