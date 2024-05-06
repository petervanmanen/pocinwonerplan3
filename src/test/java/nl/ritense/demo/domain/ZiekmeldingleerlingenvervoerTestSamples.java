package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ZiekmeldingleerlingenvervoerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ziekmeldingleerlingenvervoer getZiekmeldingleerlingenvervoerSample1() {
        return new Ziekmeldingleerlingenvervoer().id(1L);
    }

    public static Ziekmeldingleerlingenvervoer getZiekmeldingleerlingenvervoerSample2() {
        return new Ziekmeldingleerlingenvervoer().id(2L);
    }

    public static Ziekmeldingleerlingenvervoer getZiekmeldingleerlingenvervoerRandomSampleGenerator() {
        return new Ziekmeldingleerlingenvervoer().id(longCount.incrementAndGet());
    }
}
