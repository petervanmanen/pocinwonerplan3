package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AuteurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Auteur getAuteurSample1() {
        return new Auteur().id(1L);
    }

    public static Auteur getAuteurSample2() {
        return new Auteur().id(2L);
    }

    public static Auteur getAuteurRandomSampleGenerator() {
        return new Auteur().id(longCount.incrementAndGet());
    }
}
