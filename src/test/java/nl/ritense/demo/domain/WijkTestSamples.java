package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WijkTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Wijk getWijkSample1() {
        return new Wijk()
            .id(1L)
            .datumeinde("datumeinde1")
            .geometrie("geometrie1")
            .identificatie("identificatie1")
            .status("status1")
            .versie("versie1")
            .wijkcode("wijkcode1")
            .wijknaam("wijknaam1");
    }

    public static Wijk getWijkSample2() {
        return new Wijk()
            .id(2L)
            .datumeinde("datumeinde2")
            .geometrie("geometrie2")
            .identificatie("identificatie2")
            .status("status2")
            .versie("versie2")
            .wijkcode("wijkcode2")
            .wijknaam("wijknaam2");
    }

    public static Wijk getWijkRandomSampleGenerator() {
        return new Wijk()
            .id(longCount.incrementAndGet())
            .datumeinde(UUID.randomUUID().toString())
            .geometrie(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .versie(UUID.randomUUID().toString())
            .wijkcode(UUID.randomUUID().toString())
            .wijknaam(UUID.randomUUID().toString());
    }
}
