package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TentoonstellingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Tentoonstelling getTentoonstellingSample1() {
        return new Tentoonstelling().id(1L).omschrijving("omschrijving1").subtitel("subtitel1").titel("titel1");
    }

    public static Tentoonstelling getTentoonstellingSample2() {
        return new Tentoonstelling().id(2L).omschrijving("omschrijving2").subtitel("subtitel2").titel("titel2");
    }

    public static Tentoonstelling getTentoonstellingRandomSampleGenerator() {
        return new Tentoonstelling()
            .id(longCount.incrementAndGet())
            .omschrijving(UUID.randomUUID().toString())
            .subtitel(UUID.randomUUID().toString())
            .titel(UUID.randomUUID().toString());
    }
}
