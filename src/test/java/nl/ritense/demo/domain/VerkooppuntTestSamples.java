package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerkooppuntTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verkooppunt getVerkooppuntSample1() {
        return new Verkooppunt().id(1L).winkelformule("winkelformule1");
    }

    public static Verkooppunt getVerkooppuntSample2() {
        return new Verkooppunt().id(2L).winkelformule("winkelformule2");
    }

    public static Verkooppunt getVerkooppuntRandomSampleGenerator() {
        return new Verkooppunt().id(longCount.incrementAndGet()).winkelformule(UUID.randomUUID().toString());
    }
}
