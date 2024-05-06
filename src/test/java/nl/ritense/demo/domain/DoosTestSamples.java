package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DoosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Doos getDoosSample1() {
        return new Doos()
            .id(1L)
            .doosnummer("doosnummer1")
            .herkomst("herkomst1")
            .inhoud("inhoud1")
            .key("key1")
            .keymagazijnlocatie("keymagazijnlocatie1")
            .projectcd("projectcd1");
    }

    public static Doos getDoosSample2() {
        return new Doos()
            .id(2L)
            .doosnummer("doosnummer2")
            .herkomst("herkomst2")
            .inhoud("inhoud2")
            .key("key2")
            .keymagazijnlocatie("keymagazijnlocatie2")
            .projectcd("projectcd2");
    }

    public static Doos getDoosRandomSampleGenerator() {
        return new Doos()
            .id(longCount.incrementAndGet())
            .doosnummer(UUID.randomUUID().toString())
            .herkomst(UUID.randomUUID().toString())
            .inhoud(UUID.randomUUID().toString())
            .key(UUID.randomUUID().toString())
            .keymagazijnlocatie(UUID.randomUUID().toString())
            .projectcd(UUID.randomUUID().toString());
    }
}
