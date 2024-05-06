package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SubproducttypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Subproducttype getSubproducttypeSample1() {
        return new Subproducttype().id(1L).omschrijving("omschrijving1").prioriteit("prioriteit1");
    }

    public static Subproducttype getSubproducttypeSample2() {
        return new Subproducttype().id(2L).omschrijving("omschrijving2").prioriteit("prioriteit2");
    }

    public static Subproducttype getSubproducttypeRandomSampleGenerator() {
        return new Subproducttype()
            .id(longCount.incrementAndGet())
            .omschrijving(UUID.randomUUID().toString())
            .prioriteit(UUID.randomUUID().toString());
    }
}
