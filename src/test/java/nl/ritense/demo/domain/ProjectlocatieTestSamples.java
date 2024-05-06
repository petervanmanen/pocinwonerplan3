package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProjectlocatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Projectlocatie getProjectlocatieSample1() {
        return new Projectlocatie()
            .id(1L)
            .adres("adres1")
            .kadastraalperceel("kadastraalperceel1")
            .kadastralegemeente("kadastralegemeente1")
            .kadastralesectie("kadastralesectie1");
    }

    public static Projectlocatie getProjectlocatieSample2() {
        return new Projectlocatie()
            .id(2L)
            .adres("adres2")
            .kadastraalperceel("kadastraalperceel2")
            .kadastralegemeente("kadastralegemeente2")
            .kadastralesectie("kadastralesectie2");
    }

    public static Projectlocatie getProjectlocatieRandomSampleGenerator() {
        return new Projectlocatie()
            .id(longCount.incrementAndGet())
            .adres(UUID.randomUUID().toString())
            .kadastraalperceel(UUID.randomUUID().toString())
            .kadastralegemeente(UUID.randomUUID().toString())
            .kadastralesectie(UUID.randomUUID().toString());
    }
}
