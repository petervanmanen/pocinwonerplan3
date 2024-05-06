package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UitstroomredenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Uitstroomreden getUitstroomredenSample1() {
        return new Uitstroomreden().id(1L).datum("datum1").omschrijving("omschrijving1");
    }

    public static Uitstroomreden getUitstroomredenSample2() {
        return new Uitstroomreden().id(2L).datum("datum2").omschrijving("omschrijving2");
    }

    public static Uitstroomreden getUitstroomredenRandomSampleGenerator() {
        return new Uitstroomreden()
            .id(longCount.incrementAndGet())
            .datum(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
