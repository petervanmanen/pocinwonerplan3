package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IndexTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Index getIndexSample1() {
        return new Index().id(1L).indexnaam("indexnaam1").indexwaarde("indexwaarde1");
    }

    public static Index getIndexSample2() {
        return new Index().id(2L).indexnaam("indexnaam2").indexwaarde("indexwaarde2");
    }

    public static Index getIndexRandomSampleGenerator() {
        return new Index()
            .id(longCount.incrementAndGet())
            .indexnaam(UUID.randomUUID().toString())
            .indexwaarde(UUID.randomUUID().toString());
    }
}
