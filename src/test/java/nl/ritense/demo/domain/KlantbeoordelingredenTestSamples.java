package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KlantbeoordelingredenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Klantbeoordelingreden getKlantbeoordelingredenSample1() {
        return new Klantbeoordelingreden().id(1L).reden("reden1");
    }

    public static Klantbeoordelingreden getKlantbeoordelingredenSample2() {
        return new Klantbeoordelingreden().id(2L).reden("reden2");
    }

    public static Klantbeoordelingreden getKlantbeoordelingredenRandomSampleGenerator() {
        return new Klantbeoordelingreden().id(longCount.incrementAndGet()).reden(UUID.randomUUID().toString());
    }
}
