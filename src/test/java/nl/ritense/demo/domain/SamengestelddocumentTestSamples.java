package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SamengestelddocumentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Samengestelddocument getSamengestelddocumentSample1() {
        return new Samengestelddocument().id(1L);
    }

    public static Samengestelddocument getSamengestelddocumentSample2() {
        return new Samengestelddocument().id(2L);
    }

    public static Samengestelddocument getSamengestelddocumentRandomSampleGenerator() {
        return new Samengestelddocument().id(longCount.incrementAndGet());
    }
}
