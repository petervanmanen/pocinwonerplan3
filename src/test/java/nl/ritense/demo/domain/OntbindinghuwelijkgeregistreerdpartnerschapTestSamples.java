package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OntbindinghuwelijkgeregistreerdpartnerschapTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ontbindinghuwelijkgeregistreerdpartnerschap getOntbindinghuwelijkgeregistreerdpartnerschapSample1() {
        return new Ontbindinghuwelijkgeregistreerdpartnerschap()
            .id(1L)
            .buitenlandseplaatseinde("buitenlandseplaatseinde1")
            .buitenlandseregioeinde("buitenlandseregioeinde1")
            .datumeinde("datumeinde1")
            .gemeenteeinde("gemeenteeinde1")
            .landofgebiedeinde("landofgebiedeinde1")
            .omschrijvinglocatieeinde("omschrijvinglocatieeinde1")
            .redeneinde("redeneinde1");
    }

    public static Ontbindinghuwelijkgeregistreerdpartnerschap getOntbindinghuwelijkgeregistreerdpartnerschapSample2() {
        return new Ontbindinghuwelijkgeregistreerdpartnerschap()
            .id(2L)
            .buitenlandseplaatseinde("buitenlandseplaatseinde2")
            .buitenlandseregioeinde("buitenlandseregioeinde2")
            .datumeinde("datumeinde2")
            .gemeenteeinde("gemeenteeinde2")
            .landofgebiedeinde("landofgebiedeinde2")
            .omschrijvinglocatieeinde("omschrijvinglocatieeinde2")
            .redeneinde("redeneinde2");
    }

    public static Ontbindinghuwelijkgeregistreerdpartnerschap getOntbindinghuwelijkgeregistreerdpartnerschapRandomSampleGenerator() {
        return new Ontbindinghuwelijkgeregistreerdpartnerschap()
            .id(longCount.incrementAndGet())
            .buitenlandseplaatseinde(UUID.randomUUID().toString())
            .buitenlandseregioeinde(UUID.randomUUID().toString())
            .datumeinde(UUID.randomUUID().toString())
            .gemeenteeinde(UUID.randomUUID().toString())
            .landofgebiedeinde(UUID.randomUUID().toString())
            .omschrijvinglocatieeinde(UUID.randomUUID().toString())
            .redeneinde(UUID.randomUUID().toString());
    }
}
