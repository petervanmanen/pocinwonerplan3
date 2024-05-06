package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LigplaatsontheffingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ligplaatsontheffing getLigplaatsontheffingSample1() {
        return new Ligplaatsontheffing().id(1L).stickernummer("stickernummer1");
    }

    public static Ligplaatsontheffing getLigplaatsontheffingSample2() {
        return new Ligplaatsontheffing().id(2L).stickernummer("stickernummer2");
    }

    public static Ligplaatsontheffing getLigplaatsontheffingRandomSampleGenerator() {
        return new Ligplaatsontheffing().id(longCount.incrementAndGet()).stickernummer(UUID.randomUUID().toString());
    }
}
