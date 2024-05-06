package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OrganisatorischeeenheidTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Organisatorischeeenheid getOrganisatorischeeenheidSample1() {
        return new Organisatorischeeenheid()
            .id(1L)
            .datumontstaan("datumontstaan1")
            .datumopheffing("datumopheffing1")
            .emailadres("emailadres1")
            .faxnummer("faxnummer1")
            .formatie("formatie1")
            .naam("naam1")
            .naamverkort("naamverkort1")
            .omschrijving("omschrijving1")
            .organisatieidentificatie("organisatieidentificatie1")
            .telefoonnummer("telefoonnummer1")
            .toelichting("toelichting1");
    }

    public static Organisatorischeeenheid getOrganisatorischeeenheidSample2() {
        return new Organisatorischeeenheid()
            .id(2L)
            .datumontstaan("datumontstaan2")
            .datumopheffing("datumopheffing2")
            .emailadres("emailadres2")
            .faxnummer("faxnummer2")
            .formatie("formatie2")
            .naam("naam2")
            .naamverkort("naamverkort2")
            .omschrijving("omschrijving2")
            .organisatieidentificatie("organisatieidentificatie2")
            .telefoonnummer("telefoonnummer2")
            .toelichting("toelichting2");
    }

    public static Organisatorischeeenheid getOrganisatorischeeenheidRandomSampleGenerator() {
        return new Organisatorischeeenheid()
            .id(longCount.incrementAndGet())
            .datumontstaan(UUID.randomUUID().toString())
            .datumopheffing(UUID.randomUUID().toString())
            .emailadres(UUID.randomUUID().toString())
            .faxnummer(UUID.randomUUID().toString())
            .formatie(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .naamverkort(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .organisatieidentificatie(UUID.randomUUID().toString())
            .telefoonnummer(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString());
    }
}
