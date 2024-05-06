package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BatchTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Batch getBatchSample1() {
        return new Batch().id(1L).datum("datum1").nummer("nummer1").tijd("tijd1");
    }

    public static Batch getBatchSample2() {
        return new Batch().id(2L).datum("datum2").nummer("nummer2").tijd("tijd2");
    }

    public static Batch getBatchRandomSampleGenerator() {
        return new Batch()
            .id(longCount.incrementAndGet())
            .datum(UUID.randomUUID().toString())
            .nummer(UUID.randomUUID().toString())
            .tijd(UUID.randomUUID().toString());
    }
}
