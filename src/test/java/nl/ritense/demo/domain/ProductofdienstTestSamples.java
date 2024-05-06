package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProductofdienstTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Productofdienst getProductofdienstSample1() {
        return new Productofdienst().id(1L).afhandeltijd("afhandeltijd1").ingebruik("ingebruik1").naam("naam1");
    }

    public static Productofdienst getProductofdienstSample2() {
        return new Productofdienst().id(2L).afhandeltijd("afhandeltijd2").ingebruik("ingebruik2").naam("naam2");
    }

    public static Productofdienst getProductofdienstRandomSampleGenerator() {
        return new Productofdienst()
            .id(longCount.incrementAndGet())
            .afhandeltijd(UUID.randomUUID().toString())
            .ingebruik(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString());
    }
}
