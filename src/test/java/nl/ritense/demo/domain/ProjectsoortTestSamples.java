package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProjectsoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Projectsoort getProjectsoortSample1() {
        return new Projectsoort().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Projectsoort getProjectsoortSample2() {
        return new Projectsoort().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Projectsoort getProjectsoortRandomSampleGenerator() {
        return new Projectsoort()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
