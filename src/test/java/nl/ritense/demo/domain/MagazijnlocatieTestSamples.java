package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MagazijnlocatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Magazijnlocatie getMagazijnlocatieSample1() {
        return new Magazijnlocatie().id(1L).key("key1").vaknummer("vaknummer1").volgletter("volgletter1");
    }

    public static Magazijnlocatie getMagazijnlocatieSample2() {
        return new Magazijnlocatie().id(2L).key("key2").vaknummer("vaknummer2").volgletter("volgletter2");
    }

    public static Magazijnlocatie getMagazijnlocatieRandomSampleGenerator() {
        return new Magazijnlocatie()
            .id(longCount.incrementAndGet())
            .key(UUID.randomUUID().toString())
            .vaknummer(UUID.randomUUID().toString())
            .volgletter(UUID.randomUUID().toString());
    }
}
