package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KenmerkenzaakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kenmerkenzaak getKenmerkenzaakSample1() {
        return new Kenmerkenzaak().id(1L).kenmerk("kenmerk1").kenmerkbron("kenmerkbron1");
    }

    public static Kenmerkenzaak getKenmerkenzaakSample2() {
        return new Kenmerkenzaak().id(2L).kenmerk("kenmerk2").kenmerkbron("kenmerkbron2");
    }

    public static Kenmerkenzaak getKenmerkenzaakRandomSampleGenerator() {
        return new Kenmerkenzaak()
            .id(longCount.incrementAndGet())
            .kenmerk(UUID.randomUUID().toString())
            .kenmerkbron(UUID.randomUUID().toString());
    }
}
