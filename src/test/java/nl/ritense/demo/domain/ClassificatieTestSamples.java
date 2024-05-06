package nl.ritense.demo.domain;

import java.util.UUID;

public class ClassificatieTestSamples {

    public static Classificatie getClassificatieSample1() {
        return new Classificatie()
            .bevatpersoonsgegevens("bevatpersoonsgegevens1")
            .gerelateerdpersoonsgegevens("gerelateerdpersoonsgegevens1")
            .id("id1");
    }

    public static Classificatie getClassificatieSample2() {
        return new Classificatie()
            .bevatpersoonsgegevens("bevatpersoonsgegevens2")
            .gerelateerdpersoonsgegevens("gerelateerdpersoonsgegevens2")
            .id("id2");
    }

    public static Classificatie getClassificatieRandomSampleGenerator() {
        return new Classificatie()
            .bevatpersoonsgegevens(UUID.randomUUID().toString())
            .gerelateerdpersoonsgegevens(UUID.randomUUID().toString())
            .id(UUID.randomUUID().toString());
    }
}
