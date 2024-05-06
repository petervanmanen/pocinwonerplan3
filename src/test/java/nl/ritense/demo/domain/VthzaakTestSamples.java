package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VthzaakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vthzaak getVthzaakSample1() {
        return new Vthzaak()
            .id(1L)
            .behandelaar("behandelaar1")
            .bevoegdgezag("bevoegdgezag1")
            .prioritering("prioritering1")
            .teambehandelaar("teambehandelaar1")
            .uitvoerendeinstantie("uitvoerendeinstantie1")
            .verkamering("verkamering1");
    }

    public static Vthzaak getVthzaakSample2() {
        return new Vthzaak()
            .id(2L)
            .behandelaar("behandelaar2")
            .bevoegdgezag("bevoegdgezag2")
            .prioritering("prioritering2")
            .teambehandelaar("teambehandelaar2")
            .uitvoerendeinstantie("uitvoerendeinstantie2")
            .verkamering("verkamering2");
    }

    public static Vthzaak getVthzaakRandomSampleGenerator() {
        return new Vthzaak()
            .id(longCount.incrementAndGet())
            .behandelaar(UUID.randomUUID().toString())
            .bevoegdgezag(UUID.randomUUID().toString())
            .prioritering(UUID.randomUUID().toString())
            .teambehandelaar(UUID.randomUUID().toString())
            .uitvoerendeinstantie(UUID.randomUUID().toString())
            .verkamering(UUID.randomUUID().toString());
    }
}
