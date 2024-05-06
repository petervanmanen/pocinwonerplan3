package nl.ritense.demo.domain;

import java.util.UUID;

public class GeneralisatieTestSamples {

    public static Generalisatie getGeneralisatieSample1() {
        return new Generalisatie()
            .definitie("definitie1")
            .eaguid("eaguid1")
            .herkomst("herkomst1")
            .herkomstdefinitie("herkomstdefinitie1")
            .id("id1")
            .naam("naam1")
            .toelichting("toelichting1");
    }

    public static Generalisatie getGeneralisatieSample2() {
        return new Generalisatie()
            .definitie("definitie2")
            .eaguid("eaguid2")
            .herkomst("herkomst2")
            .herkomstdefinitie("herkomstdefinitie2")
            .id("id2")
            .naam("naam2")
            .toelichting("toelichting2");
    }

    public static Generalisatie getGeneralisatieRandomSampleGenerator() {
        return new Generalisatie()
            .definitie(UUID.randomUUID().toString())
            .eaguid(UUID.randomUUID().toString())
            .herkomst(UUID.randomUUID().toString())
            .herkomstdefinitie(UUID.randomUUID().toString())
            .id(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString());
    }
}
