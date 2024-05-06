package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SoortoverigbouwwerkTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Soortoverigbouwwerk getSoortoverigbouwwerkSample1() {
        return new Soortoverigbouwwerk()
            .id(1L)
            .indicatieplusbrpopulatie("indicatieplusbrpopulatie1")
            .typeoverigbouwwerk("typeoverigbouwwerk1");
    }

    public static Soortoverigbouwwerk getSoortoverigbouwwerkSample2() {
        return new Soortoverigbouwwerk()
            .id(2L)
            .indicatieplusbrpopulatie("indicatieplusbrpopulatie2")
            .typeoverigbouwwerk("typeoverigbouwwerk2");
    }

    public static Soortoverigbouwwerk getSoortoverigbouwwerkRandomSampleGenerator() {
        return new Soortoverigbouwwerk()
            .id(longCount.incrementAndGet())
            .indicatieplusbrpopulatie(UUID.randomUUID().toString())
            .typeoverigbouwwerk(UUID.randomUUID().toString());
    }
}
