package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SportmateriaalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sportmateriaal getSportmateriaalSample1() {
        return new Sportmateriaal().id(1L).naam("naam1");
    }

    public static Sportmateriaal getSportmateriaalSample2() {
        return new Sportmateriaal().id(2L).naam("naam2");
    }

    public static Sportmateriaal getSportmateriaalRandomSampleGenerator() {
        return new Sportmateriaal().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
