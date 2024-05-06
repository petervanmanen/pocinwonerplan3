package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AanduidingverblijfsrechtTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aanduidingverblijfsrecht getAanduidingverblijfsrechtSample1() {
        return new Aanduidingverblijfsrecht()
            .id(1L)
            .verblijfsrechtnummer("verblijfsrechtnummer1")
            .verblijfsrechtomschrijving("verblijfsrechtomschrijving1");
    }

    public static Aanduidingverblijfsrecht getAanduidingverblijfsrechtSample2() {
        return new Aanduidingverblijfsrecht()
            .id(2L)
            .verblijfsrechtnummer("verblijfsrechtnummer2")
            .verblijfsrechtomschrijving("verblijfsrechtomschrijving2");
    }

    public static Aanduidingverblijfsrecht getAanduidingverblijfsrechtRandomSampleGenerator() {
        return new Aanduidingverblijfsrecht()
            .id(longCount.incrementAndGet())
            .verblijfsrechtnummer(UUID.randomUUID().toString())
            .verblijfsrechtomschrijving(UUID.randomUUID().toString());
    }
}
