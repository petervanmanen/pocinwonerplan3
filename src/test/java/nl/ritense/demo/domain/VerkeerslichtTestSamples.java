package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class VerkeerslichtTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verkeerslicht getVerkeerslichtSample1() {
        return new Verkeerslicht().id(1L);
    }

    public static Verkeerslicht getVerkeerslichtSample2() {
        return new Verkeerslicht().id(2L);
    }

    public static Verkeerslicht getVerkeerslichtRandomSampleGenerator() {
        return new Verkeerslicht().id(longCount.incrementAndGet());
    }
}
