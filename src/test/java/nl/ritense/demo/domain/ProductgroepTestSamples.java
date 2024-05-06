package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProductgroepTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Productgroep getProductgroepSample1() {
        return new Productgroep().id(1L).beslisboom("beslisboom1").code("code1").omschrijving("omschrijving1");
    }

    public static Productgroep getProductgroepSample2() {
        return new Productgroep().id(2L).beslisboom("beslisboom2").code("code2").omschrijving("omschrijving2");
    }

    public static Productgroep getProductgroepRandomSampleGenerator() {
        return new Productgroep()
            .id(longCount.incrementAndGet())
            .beslisboom(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
