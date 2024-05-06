package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProgrammaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Programma getProgrammaSample1() {
        return new Programma().id(1L).naam("naam1");
    }

    public static Programma getProgrammaSample2() {
        return new Programma().id(2L).naam("naam2");
    }

    public static Programma getProgrammaRandomSampleGenerator() {
        return new Programma().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
