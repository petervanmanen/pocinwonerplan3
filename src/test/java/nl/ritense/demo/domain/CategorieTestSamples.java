package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CategorieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Categorie getCategorieSample1() {
        return new Categorie().id(1L).naam("naam1");
    }

    public static Categorie getCategorieSample2() {
        return new Categorie().id(2L).naam("naam2");
    }

    public static Categorie getCategorieRandomSampleGenerator() {
        return new Categorie().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
