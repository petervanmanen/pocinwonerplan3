package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product().id(1L).codemuseumjaarkaart("codemuseumjaarkaart1").entreekaart("entreekaart1").omschrijving("omschrijving1");
    }

    public static Product getProductSample2() {
        return new Product().id(2L).codemuseumjaarkaart("codemuseumjaarkaart2").entreekaart("entreekaart2").omschrijving("omschrijving2");
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product()
            .id(longCount.incrementAndGet())
            .codemuseumjaarkaart(UUID.randomUUID().toString())
            .entreekaart(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
