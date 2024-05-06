package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VoertuigTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Voertuig getVoertuigSample1() {
        return new Voertuig().id(1L).kenteken("kenteken1").kleur("kleur1").land("land1").merk("merk1").type("type1");
    }

    public static Voertuig getVoertuigSample2() {
        return new Voertuig().id(2L).kenteken("kenteken2").kleur("kleur2").land("land2").merk("merk2").type("type2");
    }

    public static Voertuig getVoertuigRandomSampleGenerator() {
        return new Voertuig()
            .id(longCount.incrementAndGet())
            .kenteken(UUID.randomUUID().toString())
            .kleur(UUID.randomUUID().toString())
            .land(UUID.randomUUID().toString())
            .merk(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
