package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SoortgrootteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Soortgrootte getSoortgrootteSample1() {
        return new Soortgrootte().id(1L).codesoortgrootte("codesoortgrootte1").naamsoortgrootte("naamsoortgrootte1");
    }

    public static Soortgrootte getSoortgrootteSample2() {
        return new Soortgrootte().id(2L).codesoortgrootte("codesoortgrootte2").naamsoortgrootte("naamsoortgrootte2");
    }

    public static Soortgrootte getSoortgrootteRandomSampleGenerator() {
        return new Soortgrootte()
            .id(longCount.incrementAndGet())
            .codesoortgrootte(UUID.randomUUID().toString())
            .naamsoortgrootte(UUID.randomUUID().toString());
    }
}
