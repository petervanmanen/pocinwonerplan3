package nl.ritense.demo.domain;

import java.util.UUID;

public class GegevenTestSamples {

    public static Gegeven getGegevenSample1() {
        return new Gegeven()
            .alias("alias1")
            .eaguid("eaguid1")
            .id("id1")
            .naam("naam1")
            .stereotype("stereotype1")
            .toelichting("toelichting1");
    }

    public static Gegeven getGegevenSample2() {
        return new Gegeven()
            .alias("alias2")
            .eaguid("eaguid2")
            .id("id2")
            .naam("naam2")
            .stereotype("stereotype2")
            .toelichting("toelichting2");
    }

    public static Gegeven getGegevenRandomSampleGenerator() {
        return new Gegeven()
            .alias(UUID.randomUUID().toString())
            .eaguid(UUID.randomUUID().toString())
            .id(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .stereotype(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString());
    }
}
