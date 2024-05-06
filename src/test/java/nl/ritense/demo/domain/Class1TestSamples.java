package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class Class1TestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Class1 getClass1Sample1() {
        return new Class1().id(1L).waarde("waarde1");
    }

    public static Class1 getClass1Sample2() {
        return new Class1().id(2L).waarde("waarde2");
    }

    public static Class1 getClass1RandomSampleGenerator() {
        return new Class1().id(longCount.incrementAndGet()).waarde(UUID.randomUUID().toString());
    }
}
