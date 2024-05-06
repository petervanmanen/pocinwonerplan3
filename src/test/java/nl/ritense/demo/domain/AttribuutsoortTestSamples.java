package nl.ritense.demo.domain;

import java.util.UUID;

public class AttribuutsoortTestSamples {

    public static Attribuutsoort getAttribuutsoortSample1() {
        return new Attribuutsoort()
            .definitie("definitie1")
            .domein("domein1")
            .eaguid("eaguid1")
            .herkomst("herkomst1")
            .herkomstdefinitie("herkomstdefinitie1")
            .id("id1")
            .kardinaliteit("kardinaliteit1")
            .lengte("lengte1")
            .naam("naam1")
            .patroon("patroon1")
            .precisie("precisie1")
            .stereotype("stereotype1")
            .toelichting("toelichting1");
    }

    public static Attribuutsoort getAttribuutsoortSample2() {
        return new Attribuutsoort()
            .definitie("definitie2")
            .domein("domein2")
            .eaguid("eaguid2")
            .herkomst("herkomst2")
            .herkomstdefinitie("herkomstdefinitie2")
            .id("id2")
            .kardinaliteit("kardinaliteit2")
            .lengte("lengte2")
            .naam("naam2")
            .patroon("patroon2")
            .precisie("precisie2")
            .stereotype("stereotype2")
            .toelichting("toelichting2");
    }

    public static Attribuutsoort getAttribuutsoortRandomSampleGenerator() {
        return new Attribuutsoort()
            .definitie(UUID.randomUUID().toString())
            .domein(UUID.randomUUID().toString())
            .eaguid(UUID.randomUUID().toString())
            .herkomst(UUID.randomUUID().toString())
            .herkomstdefinitie(UUID.randomUUID().toString())
            .id(UUID.randomUUID().toString())
            .kardinaliteit(UUID.randomUUID().toString())
            .lengte(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .patroon(UUID.randomUUID().toString())
            .precisie(UUID.randomUUID().toString())
            .stereotype(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString());
    }
}
