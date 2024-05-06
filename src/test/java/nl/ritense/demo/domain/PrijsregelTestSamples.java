package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PrijsregelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Prijsregel getPrijsregelSample1() {
        return new Prijsregel().id(1L).credit("credit1");
    }

    public static Prijsregel getPrijsregelSample2() {
        return new Prijsregel().id(2L).credit("credit2");
    }

    public static Prijsregel getPrijsregelRandomSampleGenerator() {
        return new Prijsregel().id(longCount.incrementAndGet()).credit(UUID.randomUUID().toString());
    }
}
