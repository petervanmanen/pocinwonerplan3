package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class NaderetoegangTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Naderetoegang getNaderetoegangSample1() {
        return new Naderetoegang().id(1L);
    }

    public static Naderetoegang getNaderetoegangSample2() {
        return new Naderetoegang().id(2L);
    }

    public static Naderetoegang getNaderetoegangRandomSampleGenerator() {
        return new Naderetoegang().id(longCount.incrementAndGet());
    }
}
