package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KademuurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kademuur getKademuurSample1() {
        return new Kademuur()
            .id(1L)
            .belastingklassenieuw("belastingklassenieuw1")
            .belastingklasseoud("belastingklasseoud1")
            .hoogtebovenkantkademuur("hoogtebovenkantkademuur1")
            .materiaalbovenkantkademuur("materiaalbovenkantkademuur1")
            .oppervlaktebovenkantkademuur("oppervlaktebovenkantkademuur1")
            .type("type1")
            .typebovenkantkademuur("typebovenkantkademuur1")
            .typefundering("typefundering1")
            .typeverankering("typeverankering1");
    }

    public static Kademuur getKademuurSample2() {
        return new Kademuur()
            .id(2L)
            .belastingklassenieuw("belastingklassenieuw2")
            .belastingklasseoud("belastingklasseoud2")
            .hoogtebovenkantkademuur("hoogtebovenkantkademuur2")
            .materiaalbovenkantkademuur("materiaalbovenkantkademuur2")
            .oppervlaktebovenkantkademuur("oppervlaktebovenkantkademuur2")
            .type("type2")
            .typebovenkantkademuur("typebovenkantkademuur2")
            .typefundering("typefundering2")
            .typeverankering("typeverankering2");
    }

    public static Kademuur getKademuurRandomSampleGenerator() {
        return new Kademuur()
            .id(longCount.incrementAndGet())
            .belastingklassenieuw(UUID.randomUUID().toString())
            .belastingklasseoud(UUID.randomUUID().toString())
            .hoogtebovenkantkademuur(UUID.randomUUID().toString())
            .materiaalbovenkantkademuur(UUID.randomUUID().toString())
            .oppervlaktebovenkantkademuur(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .typebovenkantkademuur(UUID.randomUUID().toString())
            .typefundering(UUID.randomUUID().toString())
            .typeverankering(UUID.randomUUID().toString());
    }
}
