package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LigplaatsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ligplaats getLigplaatsSample1() {
        return new Ligplaats()
            .id(1L)
            .documentnummer("documentnummer1")
            .geconstateerd("geconstateerd1")
            .geometrie("geometrie1")
            .identificatie("identificatie1")
            .status("status1")
            .versie("versie1");
    }

    public static Ligplaats getLigplaatsSample2() {
        return new Ligplaats()
            .id(2L)
            .documentnummer("documentnummer2")
            .geconstateerd("geconstateerd2")
            .geometrie("geometrie2")
            .identificatie("identificatie2")
            .status("status2")
            .versie("versie2");
    }

    public static Ligplaats getLigplaatsRandomSampleGenerator() {
        return new Ligplaats()
            .id(longCount.incrementAndGet())
            .documentnummer(UUID.randomUUID().toString())
            .geconstateerd(UUID.randomUUID().toString())
            .geometrie(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .versie(UUID.randomUUID().toString());
    }
}
