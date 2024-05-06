package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TaakveldTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Taakveld getTaakveldSample1() {
        return new Taakveld().id(1L).naam("naam1");
    }

    public static Taakveld getTaakveldSample2() {
        return new Taakveld().id(2L).naam("naam2");
    }

    public static Taakveld getTaakveldRandomSampleGenerator() {
        return new Taakveld().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
