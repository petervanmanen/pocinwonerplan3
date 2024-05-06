package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerkeerstellingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verkeerstelling getVerkeerstellingSample1() {
        return new Verkeerstelling().id(1L).aantal("aantal1").tijdtot("tijdtot1").tijdvanaf("tijdvanaf1");
    }

    public static Verkeerstelling getVerkeerstellingSample2() {
        return new Verkeerstelling().id(2L).aantal("aantal2").tijdtot("tijdtot2").tijdvanaf("tijdvanaf2");
    }

    public static Verkeerstelling getVerkeerstellingRandomSampleGenerator() {
        return new Verkeerstelling()
            .id(longCount.incrementAndGet())
            .aantal(UUID.randomUUID().toString())
            .tijdtot(UUID.randomUUID().toString())
            .tijdvanaf(UUID.randomUUID().toString());
    }
}
