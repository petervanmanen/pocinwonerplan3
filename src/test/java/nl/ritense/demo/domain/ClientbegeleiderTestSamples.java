package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ClientbegeleiderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Clientbegeleider getClientbegeleiderSample1() {
        return new Clientbegeleider().id(1L).begeleiderscode("begeleiderscode1");
    }

    public static Clientbegeleider getClientbegeleiderSample2() {
        return new Clientbegeleider().id(2L).begeleiderscode("begeleiderscode2");
    }

    public static Clientbegeleider getClientbegeleiderRandomSampleGenerator() {
        return new Clientbegeleider().id(longCount.incrementAndGet()).begeleiderscode(UUID.randomUUID().toString());
    }
}
