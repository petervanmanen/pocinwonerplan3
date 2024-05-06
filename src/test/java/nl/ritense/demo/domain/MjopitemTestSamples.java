package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MjopitemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Mjopitem getMjopitemSample1() {
        return new Mjopitem()
            .id(1L)
            .code("code1")
            .omschrijving("omschrijving1")
            .opzegtermijnaanbieder("opzegtermijnaanbieder1")
            .opzegtermijnontvanger("opzegtermijnontvanger1");
    }

    public static Mjopitem getMjopitemSample2() {
        return new Mjopitem()
            .id(2L)
            .code("code2")
            .omschrijving("omschrijving2")
            .opzegtermijnaanbieder("opzegtermijnaanbieder2")
            .opzegtermijnontvanger("opzegtermijnontvanger2");
    }

    public static Mjopitem getMjopitemRandomSampleGenerator() {
        return new Mjopitem()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .opzegtermijnaanbieder(UUID.randomUUID().toString())
            .opzegtermijnontvanger(UUID.randomUUID().toString());
    }
}
