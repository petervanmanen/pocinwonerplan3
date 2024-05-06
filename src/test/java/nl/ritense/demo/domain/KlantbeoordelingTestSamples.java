package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KlantbeoordelingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Klantbeoordeling getKlantbeoordelingSample1() {
        return new Klantbeoordeling()
            .id(1L)
            .beoordeling("beoordeling1")
            .categorie("categorie1")
            .kanaal("kanaal1")
            .onderwerp("onderwerp1")
            .subcategorie("subcategorie1");
    }

    public static Klantbeoordeling getKlantbeoordelingSample2() {
        return new Klantbeoordeling()
            .id(2L)
            .beoordeling("beoordeling2")
            .categorie("categorie2")
            .kanaal("kanaal2")
            .onderwerp("onderwerp2")
            .subcategorie("subcategorie2");
    }

    public static Klantbeoordeling getKlantbeoordelingRandomSampleGenerator() {
        return new Klantbeoordeling()
            .id(longCount.incrementAndGet())
            .beoordeling(UUID.randomUUID().toString())
            .categorie(UUID.randomUUID().toString())
            .kanaal(UUID.randomUUID().toString())
            .onderwerp(UUID.randomUUID().toString())
            .subcategorie(UUID.randomUUID().toString());
    }
}
