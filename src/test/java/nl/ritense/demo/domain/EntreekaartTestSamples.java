package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EntreekaartTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Entreekaart getEntreekaartSample1() {
        return new Entreekaart().id(1L).rondleiding("rondleiding1");
    }

    public static Entreekaart getEntreekaartSample2() {
        return new Entreekaart().id(2L).rondleiding("rondleiding2");
    }

    public static Entreekaart getEntreekaartRandomSampleGenerator() {
        return new Entreekaart().id(longCount.incrementAndGet()).rondleiding(UUID.randomUUID().toString());
    }
}
