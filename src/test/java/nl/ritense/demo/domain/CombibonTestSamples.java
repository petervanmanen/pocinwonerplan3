package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CombibonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Combibon getCombibonSample1() {
        return new Combibon().id(1L).sanctie("sanctie1");
    }

    public static Combibon getCombibonSample2() {
        return new Combibon().id(2L).sanctie("sanctie2");
    }

    public static Combibon getCombibonRandomSampleGenerator() {
        return new Combibon().id(longCount.incrementAndGet()).sanctie(UUID.randomUUID().toString());
    }
}
