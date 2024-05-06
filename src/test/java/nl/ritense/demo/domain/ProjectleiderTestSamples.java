package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProjectleiderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Projectleider getProjectleiderSample1() {
        return new Projectleider().id(1L).naam("naam1");
    }

    public static Projectleider getProjectleiderSample2() {
        return new Projectleider().id(2L).naam("naam2");
    }

    public static Projectleider getProjectleiderRandomSampleGenerator() {
        return new Projectleider().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
