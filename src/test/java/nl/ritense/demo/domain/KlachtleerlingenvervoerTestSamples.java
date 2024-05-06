package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class KlachtleerlingenvervoerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Klachtleerlingenvervoer getKlachtleerlingenvervoerSample1() {
        return new Klachtleerlingenvervoer().id(1L);
    }

    public static Klachtleerlingenvervoer getKlachtleerlingenvervoerSample2() {
        return new Klachtleerlingenvervoer().id(2L);
    }

    public static Klachtleerlingenvervoer getKlachtleerlingenvervoerRandomSampleGenerator() {
        return new Klachtleerlingenvervoer().id(longCount.incrementAndGet());
    }
}
