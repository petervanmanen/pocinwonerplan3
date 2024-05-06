package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KlimplantTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Klimplant getKlimplantSample1() {
        return new Klimplant()
            .id(1L)
            .hoogte("hoogte1")
            .knipfrequentie("knipfrequentie1")
            .knipoppervlakte("knipoppervlakte1")
            .ondersteuningsvorm("ondersteuningsvorm1")
            .type("type1");
    }

    public static Klimplant getKlimplantSample2() {
        return new Klimplant()
            .id(2L)
            .hoogte("hoogte2")
            .knipfrequentie("knipfrequentie2")
            .knipoppervlakte("knipoppervlakte2")
            .ondersteuningsvorm("ondersteuningsvorm2")
            .type("type2");
    }

    public static Klimplant getKlimplantRandomSampleGenerator() {
        return new Klimplant()
            .id(longCount.incrementAndGet())
            .hoogte(UUID.randomUUID().toString())
            .knipfrequentie(UUID.randomUUID().toString())
            .knipoppervlakte(UUID.randomUUID().toString())
            .ondersteuningsvorm(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
