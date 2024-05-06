package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RelatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Relatie getRelatieSample1() {
        return new Relatie().id(1L).relatiesoort("relatiesoort1");
    }

    public static Relatie getRelatieSample2() {
        return new Relatie().id(2L).relatiesoort("relatiesoort2");
    }

    public static Relatie getRelatieRandomSampleGenerator() {
        return new Relatie().id(longCount.incrementAndGet()).relatiesoort(UUID.randomUUID().toString());
    }
}
