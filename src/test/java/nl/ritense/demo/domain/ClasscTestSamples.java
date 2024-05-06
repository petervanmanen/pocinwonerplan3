package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ClasscTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Classc getClasscSample1() {
        return new Classc().id(1L).naam("naam1");
    }

    public static Classc getClasscSample2() {
        return new Classc().id(2L).naam("naam2");
    }

    public static Classc getClasscRandomSampleGenerator() {
        return new Classc().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
