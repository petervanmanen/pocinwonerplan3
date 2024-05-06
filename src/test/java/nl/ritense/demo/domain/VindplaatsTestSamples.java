package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VindplaatsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vindplaats getVindplaatsSample1() {
        return new Vindplaats()
            .id(1L)
            .aard("aard1")
            .begindatering("begindatering1")
            .beschrijving("beschrijving1")
            .bibliografie("bibliografie1")
            .datering("datering1")
            .depot("depot1")
            .documentatie("documentatie1")
            .einddatering("einddatering1")
            .gemeente("gemeente1")
            .locatie("locatie1")
            .mobilia("mobilia1")
            .onderzoek("onderzoek1")
            .projectcode("projectcode1")
            .vindplaats("vindplaats1");
    }

    public static Vindplaats getVindplaatsSample2() {
        return new Vindplaats()
            .id(2L)
            .aard("aard2")
            .begindatering("begindatering2")
            .beschrijving("beschrijving2")
            .bibliografie("bibliografie2")
            .datering("datering2")
            .depot("depot2")
            .documentatie("documentatie2")
            .einddatering("einddatering2")
            .gemeente("gemeente2")
            .locatie("locatie2")
            .mobilia("mobilia2")
            .onderzoek("onderzoek2")
            .projectcode("projectcode2")
            .vindplaats("vindplaats2");
    }

    public static Vindplaats getVindplaatsRandomSampleGenerator() {
        return new Vindplaats()
            .id(longCount.incrementAndGet())
            .aard(UUID.randomUUID().toString())
            .begindatering(UUID.randomUUID().toString())
            .beschrijving(UUID.randomUUID().toString())
            .bibliografie(UUID.randomUUID().toString())
            .datering(UUID.randomUUID().toString())
            .depot(UUID.randomUUID().toString())
            .documentatie(UUID.randomUUID().toString())
            .einddatering(UUID.randomUUID().toString())
            .gemeente(UUID.randomUUID().toString())
            .locatie(UUID.randomUUID().toString())
            .mobilia(UUID.randomUUID().toString())
            .onderzoek(UUID.randomUUID().toString())
            .projectcode(UUID.randomUUID().toString())
            .vindplaats(UUID.randomUUID().toString());
    }
}
