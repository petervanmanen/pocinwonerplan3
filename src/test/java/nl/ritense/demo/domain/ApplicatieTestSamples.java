package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ApplicatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Applicatie getApplicatieSample1() {
        return new Applicatie()
            .id(1L)
            .applicatieurl("applicatieurl1")
            .beheerstatus("beheerstatus1")
            .beleidsdomein("beleidsdomein1")
            .categorie("categorie1")
            .guid("guid1")
            .naam("naam1")
            .omschrijving("omschrijving1")
            .packagingstatus("packagingstatus1");
    }

    public static Applicatie getApplicatieSample2() {
        return new Applicatie()
            .id(2L)
            .applicatieurl("applicatieurl2")
            .beheerstatus("beheerstatus2")
            .beleidsdomein("beleidsdomein2")
            .categorie("categorie2")
            .guid("guid2")
            .naam("naam2")
            .omschrijving("omschrijving2")
            .packagingstatus("packagingstatus2");
    }

    public static Applicatie getApplicatieRandomSampleGenerator() {
        return new Applicatie()
            .id(longCount.incrementAndGet())
            .applicatieurl(UUID.randomUUID().toString())
            .beheerstatus(UUID.randomUUID().toString())
            .beleidsdomein(UUID.randomUUID().toString())
            .categorie(UUID.randomUUID().toString())
            .guid(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .packagingstatus(UUID.randomUUID().toString());
    }
}
