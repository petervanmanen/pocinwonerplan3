package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ExternebronTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Externebron getExternebronSample1() {
        return new Externebron().id(1L).guid("guid1").naam("naam1");
    }

    public static Externebron getExternebronSample2() {
        return new Externebron().id(2L).guid("guid2").naam("naam2");
    }

    public static Externebron getExternebronRandomSampleGenerator() {
        return new Externebron().id(longCount.incrementAndGet()).guid(UUID.randomUUID().toString()).naam(UUID.randomUUID().toString());
    }
}
