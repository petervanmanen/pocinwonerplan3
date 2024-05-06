package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OfferteaanvraagTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Offerteaanvraag getOfferteaanvraagSample1() {
        return new Offerteaanvraag().id(1L).datumsluiting("datumsluiting1").naam("naam1").omschrijving("omschrijving1");
    }

    public static Offerteaanvraag getOfferteaanvraagSample2() {
        return new Offerteaanvraag().id(2L).datumsluiting("datumsluiting2").naam("naam2").omschrijving("omschrijving2");
    }

    public static Offerteaanvraag getOfferteaanvraagRandomSampleGenerator() {
        return new Offerteaanvraag()
            .id(longCount.incrementAndGet())
            .datumsluiting(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
