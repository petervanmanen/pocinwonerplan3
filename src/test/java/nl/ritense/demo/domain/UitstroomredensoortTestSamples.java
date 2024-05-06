package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UitstroomredensoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Uitstroomredensoort getUitstroomredensoortSample1() {
        return new Uitstroomredensoort().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Uitstroomredensoort getUitstroomredensoortSample2() {
        return new Uitstroomredensoort().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Uitstroomredensoort getUitstroomredensoortRandomSampleGenerator() {
        return new Uitstroomredensoort()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
