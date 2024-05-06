package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class KadastralemutatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kadastralemutatie getKadastralemutatieSample1() {
        return new Kadastralemutatie().id(1L);
    }

    public static Kadastralemutatie getKadastralemutatieSample2() {
        return new Kadastralemutatie().id(2L);
    }

    public static Kadastralemutatie getKadastralemutatieRandomSampleGenerator() {
        return new Kadastralemutatie().id(longCount.incrementAndGet());
    }
}
