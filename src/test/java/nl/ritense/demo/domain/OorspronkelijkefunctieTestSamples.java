package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OorspronkelijkefunctieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Oorspronkelijkefunctie getOorspronkelijkefunctieSample1() {
        return new Oorspronkelijkefunctie()
            .id(1L)
            .functie("functie1")
            .functiesoort("functiesoort1")
            .hoofdcategorie("hoofdcategorie1")
            .hoofdfunctie("hoofdfunctie1")
            .subcategorie("subcategorie1")
            .toelichting("toelichting1")
            .verbijzondering("verbijzondering1");
    }

    public static Oorspronkelijkefunctie getOorspronkelijkefunctieSample2() {
        return new Oorspronkelijkefunctie()
            .id(2L)
            .functie("functie2")
            .functiesoort("functiesoort2")
            .hoofdcategorie("hoofdcategorie2")
            .hoofdfunctie("hoofdfunctie2")
            .subcategorie("subcategorie2")
            .toelichting("toelichting2")
            .verbijzondering("verbijzondering2");
    }

    public static Oorspronkelijkefunctie getOorspronkelijkefunctieRandomSampleGenerator() {
        return new Oorspronkelijkefunctie()
            .id(longCount.incrementAndGet())
            .functie(UUID.randomUUID().toString())
            .functiesoort(UUID.randomUUID().toString())
            .hoofdcategorie(UUID.randomUUID().toString())
            .hoofdfunctie(UUID.randomUUID().toString())
            .subcategorie(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString())
            .verbijzondering(UUID.randomUUID().toString());
    }
}
