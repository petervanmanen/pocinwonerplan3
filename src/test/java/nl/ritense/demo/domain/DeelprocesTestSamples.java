package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class DeelprocesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Deelproces getDeelprocesSample1() {
        return new Deelproces().id(1L);
    }

    public static Deelproces getDeelprocesSample2() {
        return new Deelproces().id(2L);
    }

    public static Deelproces getDeelprocesRandomSampleGenerator() {
        return new Deelproces().id(longCount.incrementAndGet());
    }
}
