package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BouwtypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bouwtype getBouwtypeSample1() {
        return new Bouwtype().id(1L).hoofdcategorie("hoofdcategorie1").subcategorie("subcategorie1").toelichting("toelichting1");
    }

    public static Bouwtype getBouwtypeSample2() {
        return new Bouwtype().id(2L).hoofdcategorie("hoofdcategorie2").subcategorie("subcategorie2").toelichting("toelichting2");
    }

    public static Bouwtype getBouwtypeRandomSampleGenerator() {
        return new Bouwtype()
            .id(longCount.incrementAndGet())
            .hoofdcategorie(UUID.randomUUID().toString())
            .subcategorie(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString());
    }
}
