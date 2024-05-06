package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerlengingzaakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verlengingzaak getVerlengingzaakSample1() {
        return new Verlengingzaak().id(1L).duurverlenging("duurverlenging1").redenverlenging("redenverlenging1");
    }

    public static Verlengingzaak getVerlengingzaakSample2() {
        return new Verlengingzaak().id(2L).duurverlenging("duurverlenging2").redenverlenging("redenverlenging2");
    }

    public static Verlengingzaak getVerlengingzaakRandomSampleGenerator() {
        return new Verlengingzaak()
            .id(longCount.incrementAndGet())
            .duurverlenging(UUID.randomUUID().toString())
            .redenverlenging(UUID.randomUUID().toString());
    }
}
