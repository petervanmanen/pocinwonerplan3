package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArchiefcategorieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Archiefcategorie getArchiefcategorieSample1() {
        return new Archiefcategorie().id(1L).naam("naam1").nummer("nummer1").omschrijving("omschrijving1");
    }

    public static Archiefcategorie getArchiefcategorieSample2() {
        return new Archiefcategorie().id(2L).naam("naam2").nummer("nummer2").omschrijving("omschrijving2");
    }

    public static Archiefcategorie getArchiefcategorieRandomSampleGenerator() {
        return new Archiefcategorie()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .nummer(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
