package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IncidentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Incident getIncidentSample1() {
        return new Incident().id(1L).locatie("locatie1").naam("naam1").omschrijving("omschrijving1");
    }

    public static Incident getIncidentSample2() {
        return new Incident().id(2L).locatie("locatie2").naam("naam2").omschrijving("omschrijving2");
    }

    public static Incident getIncidentRandomSampleGenerator() {
        return new Incident()
            .id(longCount.incrementAndGet())
            .locatie(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
