package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ClassaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Classa getClassaSample1() {
        return new Classa().id(1L).naam("naam1");
    }

    public static Classa getClassaSample2() {
        return new Classa().id(2L).naam("naam2");
    }

    public static Classa getClassaRandomSampleGenerator() {
        return new Classa().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
