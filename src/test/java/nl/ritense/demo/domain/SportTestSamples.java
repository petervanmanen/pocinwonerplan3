package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SportTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sport getSportSample1() {
        return new Sport().id(1L).naam("naam1");
    }

    public static Sport getSportSample2() {
        return new Sport().id(2L).naam("naam2");
    }

    public static Sport getSportRandomSampleGenerator() {
        return new Sport().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
