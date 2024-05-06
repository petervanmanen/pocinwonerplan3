package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KadastraalperceelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kadastraalperceel getKadastraalperceelSample1() {
        return new Kadastraalperceel()
            .id(1L)
            .aanduidingsoortgrootte("aanduidingsoortgrootte1")
            .begrenzingperceel("begrenzingperceel1")
            .grootteperceel("grootteperceel1")
            .indicatiedeelperceel("indicatiedeelperceel1")
            .omschrijvingdeelperceel("omschrijvingdeelperceel1")
            .plaatscoordinatenperceel("plaatscoordinatenperceel1");
    }

    public static Kadastraalperceel getKadastraalperceelSample2() {
        return new Kadastraalperceel()
            .id(2L)
            .aanduidingsoortgrootte("aanduidingsoortgrootte2")
            .begrenzingperceel("begrenzingperceel2")
            .grootteperceel("grootteperceel2")
            .indicatiedeelperceel("indicatiedeelperceel2")
            .omschrijvingdeelperceel("omschrijvingdeelperceel2")
            .plaatscoordinatenperceel("plaatscoordinatenperceel2");
    }

    public static Kadastraalperceel getKadastraalperceelRandomSampleGenerator() {
        return new Kadastraalperceel()
            .id(longCount.incrementAndGet())
            .aanduidingsoortgrootte(UUID.randomUUID().toString())
            .begrenzingperceel(UUID.randomUUID().toString())
            .grootteperceel(UUID.randomUUID().toString())
            .indicatiedeelperceel(UUID.randomUUID().toString())
            .omschrijvingdeelperceel(UUID.randomUUID().toString())
            .plaatscoordinatenperceel(UUID.randomUUID().toString());
    }
}
