package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KostenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kosten getKostenSample1() {
        return new Kosten()
            .id(1L)
            .aangemaaktdoor("aangemaaktdoor1")
            .aantal("aantal1")
            .bedrag("bedrag1")
            .bedragtotaal("bedragtotaal1")
            .eenheid("eenheid1")
            .geaccordeerd("geaccordeerd1")
            .gemuteerddoor("gemuteerddoor1")
            .naam("naam1")
            .omschrijving("omschrijving1")
            .opbasisvangrondslag("opbasisvangrondslag1")
            .tarief("tarief1")
            .type("type1")
            .vastgesteldbedrag("vastgesteldbedrag1");
    }

    public static Kosten getKostenSample2() {
        return new Kosten()
            .id(2L)
            .aangemaaktdoor("aangemaaktdoor2")
            .aantal("aantal2")
            .bedrag("bedrag2")
            .bedragtotaal("bedragtotaal2")
            .eenheid("eenheid2")
            .geaccordeerd("geaccordeerd2")
            .gemuteerddoor("gemuteerddoor2")
            .naam("naam2")
            .omschrijving("omschrijving2")
            .opbasisvangrondslag("opbasisvangrondslag2")
            .tarief("tarief2")
            .type("type2")
            .vastgesteldbedrag("vastgesteldbedrag2");
    }

    public static Kosten getKostenRandomSampleGenerator() {
        return new Kosten()
            .id(longCount.incrementAndGet())
            .aangemaaktdoor(UUID.randomUUID().toString())
            .aantal(UUID.randomUUID().toString())
            .bedrag(UUID.randomUUID().toString())
            .bedragtotaal(UUID.randomUUID().toString())
            .eenheid(UUID.randomUUID().toString())
            .geaccordeerd(UUID.randomUUID().toString())
            .gemuteerddoor(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .opbasisvangrondslag(UUID.randomUUID().toString())
            .tarief(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .vastgesteldbedrag(UUID.randomUUID().toString());
    }
}
