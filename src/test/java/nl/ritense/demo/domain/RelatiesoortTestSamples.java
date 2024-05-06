package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RelatiesoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Relatiesoort getRelatiesoortSample1() {
        return new Relatiesoort().id(1L);
    }

    public static Relatiesoort getRelatiesoortSample2() {
        return new Relatiesoort().id(2L);
    }

    public static Relatiesoort getRelatiesoortRandomSampleGenerator() {
        return new Relatiesoort().id(longCount.incrementAndGet());
    }
}
