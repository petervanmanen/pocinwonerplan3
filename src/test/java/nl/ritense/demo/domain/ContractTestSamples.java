package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContractTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Contract getContractSample1() {
        return new Contract()
            .id(1L)
            .autorisatiegroep("autorisatiegroep1")
            .beschrijving("beschrijving1")
            .categorie("categorie1")
            .classificatie("classificatie1")
            .contractrevisie("contractrevisie1")
            .groep("groep1")
            .interncontractid("interncontractid1")
            .interncontractrevisie("interncontractrevisie1")
            .opmerkingen("opmerkingen1")
            .status("status1")
            .type("type1")
            .voorwaarde("voorwaarde1")
            .zoekwoorden("zoekwoorden1");
    }

    public static Contract getContractSample2() {
        return new Contract()
            .id(2L)
            .autorisatiegroep("autorisatiegroep2")
            .beschrijving("beschrijving2")
            .categorie("categorie2")
            .classificatie("classificatie2")
            .contractrevisie("contractrevisie2")
            .groep("groep2")
            .interncontractid("interncontractid2")
            .interncontractrevisie("interncontractrevisie2")
            .opmerkingen("opmerkingen2")
            .status("status2")
            .type("type2")
            .voorwaarde("voorwaarde2")
            .zoekwoorden("zoekwoorden2");
    }

    public static Contract getContractRandomSampleGenerator() {
        return new Contract()
            .id(longCount.incrementAndGet())
            .autorisatiegroep(UUID.randomUUID().toString())
            .beschrijving(UUID.randomUUID().toString())
            .categorie(UUID.randomUUID().toString())
            .classificatie(UUID.randomUUID().toString())
            .contractrevisie(UUID.randomUUID().toString())
            .groep(UUID.randomUUID().toString())
            .interncontractid(UUID.randomUUID().toString())
            .interncontractrevisie(UUID.randomUUID().toString())
            .opmerkingen(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .voorwaarde(UUID.randomUUID().toString())
            .zoekwoorden(UUID.randomUUID().toString());
    }
}
