package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GemeenteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Gemeente getGemeenteSample1() {
        return new Gemeente()
            .id(1L)
            .gemeentecode("gemeentecode1")
            .gemeentenaam("gemeentenaam1")
            .gemeentenaamnen("gemeentenaamnen1")
            .geometrie("geometrie1")
            .identificatie("identificatie1")
            .versie("versie1");
    }

    public static Gemeente getGemeenteSample2() {
        return new Gemeente()
            .id(2L)
            .gemeentecode("gemeentecode2")
            .gemeentenaam("gemeentenaam2")
            .gemeentenaamnen("gemeentenaamnen2")
            .geometrie("geometrie2")
            .identificatie("identificatie2")
            .versie("versie2");
    }

    public static Gemeente getGemeenteRandomSampleGenerator() {
        return new Gemeente()
            .id(longCount.incrementAndGet())
            .gemeentecode(UUID.randomUUID().toString())
            .gemeentenaam(UUID.randomUUID().toString())
            .gemeentenaamnen(UUID.randomUUID().toString())
            .geometrie(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .versie(UUID.randomUUID().toString());
    }
}
