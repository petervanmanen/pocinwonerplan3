package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AankondigingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aankondiging getAankondigingSample1() {
        return new Aankondiging().id(1L).beschrijving("beschrijving1").categorie("categorie1").datum("datum1").naam("naam1").type("type1");
    }

    public static Aankondiging getAankondigingSample2() {
        return new Aankondiging().id(2L).beschrijving("beschrijving2").categorie("categorie2").datum("datum2").naam("naam2").type("type2");
    }

    public static Aankondiging getAankondigingRandomSampleGenerator() {
        return new Aankondiging()
            .id(longCount.incrementAndGet())
            .beschrijving(UUID.randomUUID().toString())
            .categorie(UUID.randomUUID().toString())
            .datum(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
