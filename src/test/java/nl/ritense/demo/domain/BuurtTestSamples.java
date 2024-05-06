package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BuurtTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Buurt getBuurtSample1() {
        return new Buurt()
            .id(1L)
            .buurtcode("buurtcode1")
            .buurtgeometrie("buurtgeometrie1")
            .buurtnaam("buurtnaam1")
            .identificatie("identificatie1")
            .status("status1")
            .versie("versie1");
    }

    public static Buurt getBuurtSample2() {
        return new Buurt()
            .id(2L)
            .buurtcode("buurtcode2")
            .buurtgeometrie("buurtgeometrie2")
            .buurtnaam("buurtnaam2")
            .identificatie("identificatie2")
            .status("status2")
            .versie("versie2");
    }

    public static Buurt getBuurtRandomSampleGenerator() {
        return new Buurt()
            .id(longCount.incrementAndGet())
            .buurtcode(UUID.randomUUID().toString())
            .buurtgeometrie(UUID.randomUUID().toString())
            .buurtnaam(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .versie(UUID.randomUUID().toString());
    }
}
