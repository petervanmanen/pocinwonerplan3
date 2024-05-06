package nl.ritense.demo.domain;

import java.util.UUID;

public class EobjecttypeTestSamples {

    public static Eobjecttype getEobjecttypeSample1() {
        return new Eobjecttype()
            .definitie("definitie1")
            .eaguid("eaguid1")
            .herkomst("herkomst1")
            .herkomstdefinitie("herkomstdefinitie1")
            .id("id1")
            .kwaliteit("kwaliteit1")
            .naam("naam1")
            .populatie("populatie1")
            .stereotype("stereotype1")
            .toelichting("toelichting1")
            .uniekeaanduiding("uniekeaanduiding1");
    }

    public static Eobjecttype getEobjecttypeSample2() {
        return new Eobjecttype()
            .definitie("definitie2")
            .eaguid("eaguid2")
            .herkomst("herkomst2")
            .herkomstdefinitie("herkomstdefinitie2")
            .id("id2")
            .kwaliteit("kwaliteit2")
            .naam("naam2")
            .populatie("populatie2")
            .stereotype("stereotype2")
            .toelichting("toelichting2")
            .uniekeaanduiding("uniekeaanduiding2");
    }

    public static Eobjecttype getEobjecttypeRandomSampleGenerator() {
        return new Eobjecttype()
            .definitie(UUID.randomUUID().toString())
            .eaguid(UUID.randomUUID().toString())
            .herkomst(UUID.randomUUID().toString())
            .herkomstdefinitie(UUID.randomUUID().toString())
            .id(UUID.randomUUID().toString())
            .kwaliteit(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .populatie(UUID.randomUUID().toString())
            .stereotype(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString())
            .uniekeaanduiding(UUID.randomUUID().toString());
    }
}
