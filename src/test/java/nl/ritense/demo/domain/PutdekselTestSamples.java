package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PutdekselTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Putdeksel getPutdekselSample1() {
        return new Putdeksel().id(1L).diameter("diameter1").put("put1").type("type1").vorm("vorm1");
    }

    public static Putdeksel getPutdekselSample2() {
        return new Putdeksel().id(2L).diameter("diameter2").put("put2").type("type2").vorm("vorm2");
    }

    public static Putdeksel getPutdekselRandomSampleGenerator() {
        return new Putdeksel()
            .id(longCount.incrementAndGet())
            .diameter(UUID.randomUUID().toString())
            .put(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .vorm(UUID.randomUUID().toString());
    }
}
