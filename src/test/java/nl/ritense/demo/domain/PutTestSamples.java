package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PutTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Put getPutSample1() {
        return new Put().id(1L).key("key1").projectcd("projectcd1").putnummer("putnummer1");
    }

    public static Put getPutSample2() {
        return new Put().id(2L).key("key2").projectcd("projectcd2").putnummer("putnummer2");
    }

    public static Put getPutRandomSampleGenerator() {
        return new Put()
            .id(longCount.incrementAndGet())
            .key(UUID.randomUUID().toString())
            .projectcd(UUID.randomUUID().toString())
            .putnummer(UUID.randomUUID().toString());
    }
}
