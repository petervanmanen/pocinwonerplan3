package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BeschikkingleerlingenvervoerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Beschikkingleerlingenvervoer getBeschikkingleerlingenvervoerSample1() {
        return new Beschikkingleerlingenvervoer().id(1L);
    }

    public static Beschikkingleerlingenvervoer getBeschikkingleerlingenvervoerSample2() {
        return new Beschikkingleerlingenvervoer().id(2L);
    }

    public static Beschikkingleerlingenvervoer getBeschikkingleerlingenvervoerRandomSampleGenerator() {
        return new Beschikkingleerlingenvervoer().id(longCount.incrementAndGet());
    }
}
