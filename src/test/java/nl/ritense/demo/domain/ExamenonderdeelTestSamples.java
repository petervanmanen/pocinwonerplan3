package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ExamenonderdeelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Examenonderdeel getExamenonderdeelSample1() {
        return new Examenonderdeel().id(1L);
    }

    public static Examenonderdeel getExamenonderdeelSample2() {
        return new Examenonderdeel().id(2L);
    }

    public static Examenonderdeel getExamenonderdeelRandomSampleGenerator() {
        return new Examenonderdeel().id(longCount.incrementAndGet());
    }
}
