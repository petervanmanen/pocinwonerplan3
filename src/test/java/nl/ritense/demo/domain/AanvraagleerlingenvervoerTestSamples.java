package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AanvraagleerlingenvervoerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aanvraagleerlingenvervoer getAanvraagleerlingenvervoerSample1() {
        return new Aanvraagleerlingenvervoer().id(1L);
    }

    public static Aanvraagleerlingenvervoer getAanvraagleerlingenvervoerSample2() {
        return new Aanvraagleerlingenvervoer().id(2L);
    }

    public static Aanvraagleerlingenvervoer getAanvraagleerlingenvervoerRandomSampleGenerator() {
        return new Aanvraagleerlingenvervoer().id(longCount.incrementAndGet());
    }
}
