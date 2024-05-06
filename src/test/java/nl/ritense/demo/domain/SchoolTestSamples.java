package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SchoolTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static School getSchoolSample1() {
        return new School().id(1L).naam("naam1");
    }

    public static School getSchoolSample2() {
        return new School().id(2L).naam("naam2");
    }

    public static School getSchoolRandomSampleGenerator() {
        return new School().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
