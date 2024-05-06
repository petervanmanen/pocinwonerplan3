package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KolkTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kolk getKolkSample1() {
        return new Kolk().id(1L).bereikbaarheidkolk("bereikbaarheidkolk1").risicogebied("risicogebied1").type("type1");
    }

    public static Kolk getKolkSample2() {
        return new Kolk().id(2L).bereikbaarheidkolk("bereikbaarheidkolk2").risicogebied("risicogebied2").type("type2");
    }

    public static Kolk getKolkRandomSampleGenerator() {
        return new Kolk()
            .id(longCount.incrementAndGet())
            .bereikbaarheidkolk(UUID.randomUUID().toString())
            .risicogebied(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
