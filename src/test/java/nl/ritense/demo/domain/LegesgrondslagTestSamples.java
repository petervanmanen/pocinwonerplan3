package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LegesgrondslagTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Legesgrondslag getLegesgrondslagSample1() {
        return new Legesgrondslag()
            .id(1L)
            .aangemaaktdoor("aangemaaktdoor1")
            .aantalopgegeven("aantalopgegeven1")
            .aantalvastgesteld("aantalvastgesteld1")
            .automatisch("automatisch1")
            .datumaanmaak("datumaanmaak1")
            .eenheid("eenheid1")
            .gemuteerddoor("gemuteerddoor1")
            .legesgrondslag("legesgrondslag1")
            .omschrijving("omschrijving1");
    }

    public static Legesgrondslag getLegesgrondslagSample2() {
        return new Legesgrondslag()
            .id(2L)
            .aangemaaktdoor("aangemaaktdoor2")
            .aantalopgegeven("aantalopgegeven2")
            .aantalvastgesteld("aantalvastgesteld2")
            .automatisch("automatisch2")
            .datumaanmaak("datumaanmaak2")
            .eenheid("eenheid2")
            .gemuteerddoor("gemuteerddoor2")
            .legesgrondslag("legesgrondslag2")
            .omschrijving("omschrijving2");
    }

    public static Legesgrondslag getLegesgrondslagRandomSampleGenerator() {
        return new Legesgrondslag()
            .id(longCount.incrementAndGet())
            .aangemaaktdoor(UUID.randomUUID().toString())
            .aantalopgegeven(UUID.randomUUID().toString())
            .aantalvastgesteld(UUID.randomUUID().toString())
            .automatisch(UUID.randomUUID().toString())
            .datumaanmaak(UUID.randomUUID().toString())
            .eenheid(UUID.randomUUID().toString())
            .gemuteerddoor(UUID.randomUUID().toString())
            .legesgrondslag(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
