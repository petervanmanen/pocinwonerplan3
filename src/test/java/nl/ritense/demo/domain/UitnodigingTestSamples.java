package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UitnodigingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Uitnodiging getUitnodigingSample1() {
        return new Uitnodiging().id(1L).afgewezen("afgewezen1").datum("datum1").geaccepteerd("geaccepteerd1");
    }

    public static Uitnodiging getUitnodigingSample2() {
        return new Uitnodiging().id(2L).afgewezen("afgewezen2").datum("datum2").geaccepteerd("geaccepteerd2");
    }

    public static Uitnodiging getUitnodigingRandomSampleGenerator() {
        return new Uitnodiging()
            .id(longCount.incrementAndGet())
            .afgewezen(UUID.randomUUID().toString())
            .datum(UUID.randomUUID().toString())
            .geaccepteerd(UUID.randomUUID().toString());
    }
}
