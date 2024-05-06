package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MagazijnplaatsingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Magazijnplaatsing getMagazijnplaatsingSample1() {
        return new Magazijnplaatsing()
            .id(1L)
            .beschrijving("beschrijving1")
            .herkomst("herkomst1")
            .key("key1")
            .keydoos("keydoos1")
            .keymagazijnlocatie("keymagazijnlocatie1")
            .projectcd("projectcd1");
    }

    public static Magazijnplaatsing getMagazijnplaatsingSample2() {
        return new Magazijnplaatsing()
            .id(2L)
            .beschrijving("beschrijving2")
            .herkomst("herkomst2")
            .key("key2")
            .keydoos("keydoos2")
            .keymagazijnlocatie("keymagazijnlocatie2")
            .projectcd("projectcd2");
    }

    public static Magazijnplaatsing getMagazijnplaatsingRandomSampleGenerator() {
        return new Magazijnplaatsing()
            .id(longCount.incrementAndGet())
            .beschrijving(UUID.randomUUID().toString())
            .herkomst(UUID.randomUUID().toString())
            .key(UUID.randomUUID().toString())
            .keydoos(UUID.randomUUID().toString())
            .keymagazijnlocatie(UUID.randomUUID().toString())
            .projectcd(UUID.randomUUID().toString());
    }
}
