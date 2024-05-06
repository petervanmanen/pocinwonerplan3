package nl.ritense.demo.domain;

import java.util.UUID;

public class DatatypeTestSamples {

    public static Datatype getDatatypeSample1() {
        return new Datatype()
            .definitie("definitie1")
            .domein("domein1")
            .eaguid("eaguid1")
            .herkomst("herkomst1")
            .id("id1")
            .kardinaliteit("kardinaliteit1")
            .lengte("lengte1")
            .naam("naam1")
            .patroon("patroon1")
            .toelichting("toelichting1");
    }

    public static Datatype getDatatypeSample2() {
        return new Datatype()
            .definitie("definitie2")
            .domein("domein2")
            .eaguid("eaguid2")
            .herkomst("herkomst2")
            .id("id2")
            .kardinaliteit("kardinaliteit2")
            .lengte("lengte2")
            .naam("naam2")
            .patroon("patroon2")
            .toelichting("toelichting2");
    }

    public static Datatype getDatatypeRandomSampleGenerator() {
        return new Datatype()
            .definitie(UUID.randomUUID().toString())
            .domein(UUID.randomUUID().toString())
            .eaguid(UUID.randomUUID().toString())
            .herkomst(UUID.randomUUID().toString())
            .id(UUID.randomUUID().toString())
            .kardinaliteit(UUID.randomUUID().toString())
            .lengte(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .patroon(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString());
    }
}
