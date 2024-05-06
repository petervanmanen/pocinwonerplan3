package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CmdbitemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Cmdbitem getCmdbitemSample1() {
        return new Cmdbitem().id(1L).beschrijving("beschrijving1").naam("naam1");
    }

    public static Cmdbitem getCmdbitemSample2() {
        return new Cmdbitem().id(2L).beschrijving("beschrijving2").naam("naam2");
    }

    public static Cmdbitem getCmdbitemRandomSampleGenerator() {
        return new Cmdbitem().id(longCount.incrementAndGet()).beschrijving(UUID.randomUUID().toString()).naam(UUID.randomUUID().toString());
    }
}
