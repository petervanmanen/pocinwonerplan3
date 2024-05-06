package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AfvalbakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Afvalbak getAfvalbakSample1() {
        return new Afvalbak().id(1L).type("type1").typeplus("typeplus1");
    }

    public static Afvalbak getAfvalbakSample2() {
        return new Afvalbak().id(2L).type("type2").typeplus("typeplus2");
    }

    public static Afvalbak getAfvalbakRandomSampleGenerator() {
        return new Afvalbak().id(longCount.incrementAndGet()).type(UUID.randomUUID().toString()).typeplus(UUID.randomUUID().toString());
    }
}
