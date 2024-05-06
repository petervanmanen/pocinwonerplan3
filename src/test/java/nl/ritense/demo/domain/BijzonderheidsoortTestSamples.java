package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BijzonderheidsoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bijzonderheidsoort getBijzonderheidsoortSample1() {
        return new Bijzonderheidsoort().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Bijzonderheidsoort getBijzonderheidsoortSample2() {
        return new Bijzonderheidsoort().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Bijzonderheidsoort getBijzonderheidsoortRandomSampleGenerator() {
        return new Bijzonderheidsoort()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
