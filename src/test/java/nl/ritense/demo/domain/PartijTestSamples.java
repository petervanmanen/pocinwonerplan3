package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PartijTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Partij getPartijSample1() {
        return new Partij()
            .id(1L)
            .code("code1")
            .naam("naam1")
            .soort("soort1")
            .verstrekkingsbeperkingmogelijk("verstrekkingsbeperkingmogelijk1");
    }

    public static Partij getPartijSample2() {
        return new Partij()
            .id(2L)
            .code("code2")
            .naam("naam2")
            .soort("soort2")
            .verstrekkingsbeperkingmogelijk("verstrekkingsbeperkingmogelijk2");
    }

    public static Partij getPartijRandomSampleGenerator() {
        return new Partij()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .soort(UUID.randomUUID().toString())
            .verstrekkingsbeperkingmogelijk(UUID.randomUUID().toString());
    }
}
