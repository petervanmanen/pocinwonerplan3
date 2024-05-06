package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BeperkingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Beperking getBeperkingSample1() {
        return new Beperking().id(1L).categorie("categorie1").commentaar("commentaar1").duur("duur1").wet("wet1");
    }

    public static Beperking getBeperkingSample2() {
        return new Beperking().id(2L).categorie("categorie2").commentaar("commentaar2").duur("duur2").wet("wet2");
    }

    public static Beperking getBeperkingRandomSampleGenerator() {
        return new Beperking()
            .id(longCount.incrementAndGet())
            .categorie(UUID.randomUUID().toString())
            .commentaar(UUID.randomUUID().toString())
            .duur(UUID.randomUUID().toString())
            .wet(UUID.randomUUID().toString());
    }
}
