package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SoortscheidingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Soortscheiding getSoortscheidingSample1() {
        return new Soortscheiding().id(1L).indicatieplusbrpopulatie("indicatieplusbrpopulatie1").typescheiding("typescheiding1");
    }

    public static Soortscheiding getSoortscheidingSample2() {
        return new Soortscheiding().id(2L).indicatieplusbrpopulatie("indicatieplusbrpopulatie2").typescheiding("typescheiding2");
    }

    public static Soortscheiding getSoortscheidingRandomSampleGenerator() {
        return new Soortscheiding()
            .id(longCount.incrementAndGet())
            .indicatieplusbrpopulatie(UUID.randomUUID().toString())
            .typescheiding(UUID.randomUUID().toString());
    }
}
