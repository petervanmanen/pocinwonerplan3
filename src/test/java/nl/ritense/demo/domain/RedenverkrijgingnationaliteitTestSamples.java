package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RedenverkrijgingnationaliteitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Redenverkrijgingnationaliteit getRedenverkrijgingnationaliteitSample1() {
        return new Redenverkrijgingnationaliteit()
            .id(1L)
            .omschrijvingverkrijging("omschrijvingverkrijging1")
            .redennummerverkrijging("redennummerverkrijging1");
    }

    public static Redenverkrijgingnationaliteit getRedenverkrijgingnationaliteitSample2() {
        return new Redenverkrijgingnationaliteit()
            .id(2L)
            .omschrijvingverkrijging("omschrijvingverkrijging2")
            .redennummerverkrijging("redennummerverkrijging2");
    }

    public static Redenverkrijgingnationaliteit getRedenverkrijgingnationaliteitRandomSampleGenerator() {
        return new Redenverkrijgingnationaliteit()
            .id(longCount.incrementAndGet())
            .omschrijvingverkrijging(UUID.randomUUID().toString())
            .redennummerverkrijging(UUID.randomUUID().toString());
    }
}
