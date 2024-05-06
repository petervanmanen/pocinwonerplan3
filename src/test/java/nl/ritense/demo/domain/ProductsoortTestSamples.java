package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProductsoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Productsoort getProductsoortSample1() {
        return new Productsoort().id(1L).code("code1").omschrijving("omschrijving1").tariefperiode("tariefperiode1");
    }

    public static Productsoort getProductsoortSample2() {
        return new Productsoort().id(2L).code("code2").omschrijving("omschrijving2").tariefperiode("tariefperiode2");
    }

    public static Productsoort getProductsoortRandomSampleGenerator() {
        return new Productsoort()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .tariefperiode(UUID.randomUUID().toString());
    }
}
