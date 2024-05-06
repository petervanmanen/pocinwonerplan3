package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OmzetgroepTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Omzetgroep getOmzetgroepSample1() {
        return new Omzetgroep().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Omzetgroep getOmzetgroepSample2() {
        return new Omzetgroep().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Omzetgroep getOmzetgroepRandomSampleGenerator() {
        return new Omzetgroep()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
