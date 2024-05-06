package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WoonplaatsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Woonplaats getWoonplaatsSample1() {
        return new Woonplaats()
            .id(1L)
            .geometrie("geometrie1")
            .identificatie("identificatie1")
            .status("status1")
            .versie("versie1")
            .woonplaatsnaam("woonplaatsnaam1")
            .woonplaatsnaamnen("woonplaatsnaamnen1");
    }

    public static Woonplaats getWoonplaatsSample2() {
        return new Woonplaats()
            .id(2L)
            .geometrie("geometrie2")
            .identificatie("identificatie2")
            .status("status2")
            .versie("versie2")
            .woonplaatsnaam("woonplaatsnaam2")
            .woonplaatsnaamnen("woonplaatsnaamnen2");
    }

    public static Woonplaats getWoonplaatsRandomSampleGenerator() {
        return new Woonplaats()
            .id(longCount.incrementAndGet())
            .geometrie(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .versie(UUID.randomUUID().toString())
            .woonplaatsnaam(UUID.randomUUID().toString())
            .woonplaatsnaamnen(UUID.randomUUID().toString());
    }
}
