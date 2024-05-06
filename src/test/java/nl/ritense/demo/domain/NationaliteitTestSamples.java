package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NationaliteitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Nationaliteit getNationaliteitSample1() {
        return new Nationaliteit()
            .id(1L)
            .datumopnamen("datumopnamen1")
            .nationaliteit("nationaliteit1")
            .nationaliteitcode("nationaliteitcode1")
            .redenverkrijgingnederlandsenationaliteit("redenverkrijgingnederlandsenationaliteit1")
            .redenverliesnederlandsenationaliteit("redenverliesnederlandsenationaliteit1");
    }

    public static Nationaliteit getNationaliteitSample2() {
        return new Nationaliteit()
            .id(2L)
            .datumopnamen("datumopnamen2")
            .nationaliteit("nationaliteit2")
            .nationaliteitcode("nationaliteitcode2")
            .redenverkrijgingnederlandsenationaliteit("redenverkrijgingnederlandsenationaliteit2")
            .redenverliesnederlandsenationaliteit("redenverliesnederlandsenationaliteit2");
    }

    public static Nationaliteit getNationaliteitRandomSampleGenerator() {
        return new Nationaliteit()
            .id(longCount.incrementAndGet())
            .datumopnamen(UUID.randomUUID().toString())
            .nationaliteit(UUID.randomUUID().toString())
            .nationaliteitcode(UUID.randomUUID().toString())
            .redenverkrijgingnederlandsenationaliteit(UUID.randomUUID().toString())
            .redenverliesnederlandsenationaliteit(UUID.randomUUID().toString());
    }
}
