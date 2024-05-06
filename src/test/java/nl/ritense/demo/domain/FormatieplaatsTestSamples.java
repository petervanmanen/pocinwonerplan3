package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FormatieplaatsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Formatieplaats getFormatieplaatsSample1() {
        return new Formatieplaats().id(1L).urenperweek("urenperweek1");
    }

    public static Formatieplaats getFormatieplaatsSample2() {
        return new Formatieplaats().id(2L).urenperweek("urenperweek2");
    }

    public static Formatieplaats getFormatieplaatsRandomSampleGenerator() {
        return new Formatieplaats().id(longCount.incrementAndGet()).urenperweek(UUID.randomUUID().toString());
    }
}
