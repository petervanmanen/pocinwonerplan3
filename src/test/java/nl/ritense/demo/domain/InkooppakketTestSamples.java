package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InkooppakketTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Inkooppakket getInkooppakketSample1() {
        return new Inkooppakket().id(1L).code("code1").naam("naam1").type("type1");
    }

    public static Inkooppakket getInkooppakketSample2() {
        return new Inkooppakket().id(2L).code("code2").naam("naam2").type("type2");
    }

    public static Inkooppakket getInkooppakketRandomSampleGenerator() {
        return new Inkooppakket()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
