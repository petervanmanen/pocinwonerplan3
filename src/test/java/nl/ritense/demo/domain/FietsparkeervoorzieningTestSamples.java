package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FietsparkeervoorzieningTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Fietsparkeervoorziening getFietsparkeervoorzieningSample1() {
        return new Fietsparkeervoorziening().id(1L).aantalparkeerplaatsen("aantalparkeerplaatsen1").type("type1").typeplus("typeplus1");
    }

    public static Fietsparkeervoorziening getFietsparkeervoorzieningSample2() {
        return new Fietsparkeervoorziening().id(2L).aantalparkeerplaatsen("aantalparkeerplaatsen2").type("type2").typeplus("typeplus2");
    }

    public static Fietsparkeervoorziening getFietsparkeervoorzieningRandomSampleGenerator() {
        return new Fietsparkeervoorziening()
            .id(longCount.incrementAndGet())
            .aantalparkeerplaatsen(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .typeplus(UUID.randomUUID().toString());
    }
}
