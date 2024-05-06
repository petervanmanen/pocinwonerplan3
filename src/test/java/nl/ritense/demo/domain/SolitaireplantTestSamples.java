package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SolitaireplantTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Solitaireplant getSolitaireplantSample1() {
        return new Solitaireplant().id(1L).hoogte("hoogte1").type("type1");
    }

    public static Solitaireplant getSolitaireplantSample2() {
        return new Solitaireplant().id(2L).hoogte("hoogte2").type("type2");
    }

    public static Solitaireplant getSolitaireplantRandomSampleGenerator() {
        return new Solitaireplant().id(longCount.incrementAndGet()).hoogte(UUID.randomUUID().toString()).type(UUID.randomUUID().toString());
    }
}
