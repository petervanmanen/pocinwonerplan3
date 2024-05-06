package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ArtikelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Artikel getArtikelSample1() {
        return new Artikel().id(1L);
    }

    public static Artikel getArtikelSample2() {
        return new Artikel().id(2L);
    }

    public static Artikel getArtikelRandomSampleGenerator() {
        return new Artikel().id(longCount.incrementAndGet());
    }
}
