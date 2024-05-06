package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NederlandsenationaliteitingeschrevenpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Nederlandsenationaliteitingeschrevenpersoon getNederlandsenationaliteitingeschrevenpersoonSample1() {
        return new Nederlandsenationaliteitingeschrevenpersoon()
            .id(1L)
            .aanduidingbijzondernederlanderschap("aanduidingbijzondernederlanderschap1")
            .nationaliteit("nationaliteit1")
            .redenverkrijgingnederlandsenationaliteit("redenverkrijgingnederlandsenationaliteit1")
            .redenverliesnederlandsenationaliteit("redenverliesnederlandsenationaliteit1");
    }

    public static Nederlandsenationaliteitingeschrevenpersoon getNederlandsenationaliteitingeschrevenpersoonSample2() {
        return new Nederlandsenationaliteitingeschrevenpersoon()
            .id(2L)
            .aanduidingbijzondernederlanderschap("aanduidingbijzondernederlanderschap2")
            .nationaliteit("nationaliteit2")
            .redenverkrijgingnederlandsenationaliteit("redenverkrijgingnederlandsenationaliteit2")
            .redenverliesnederlandsenationaliteit("redenverliesnederlandsenationaliteit2");
    }

    public static Nederlandsenationaliteitingeschrevenpersoon getNederlandsenationaliteitingeschrevenpersoonRandomSampleGenerator() {
        return new Nederlandsenationaliteitingeschrevenpersoon()
            .id(longCount.incrementAndGet())
            .aanduidingbijzondernederlanderschap(UUID.randomUUID().toString())
            .nationaliteit(UUID.randomUUID().toString())
            .redenverkrijgingnederlandsenationaliteit(UUID.randomUUID().toString())
            .redenverliesnederlandsenationaliteit(UUID.randomUUID().toString());
    }
}
