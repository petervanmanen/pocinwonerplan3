package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerplichtingwmojeugdTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verplichtingwmojeugd getVerplichtingwmojeugdSample1() {
        return new Verplichtingwmojeugd()
            .id(1L)
            .budgetsoort("budgetsoort1")
            .budgetsoortgroep("budgetsoortgroep1")
            .einddatumgepland("einddatumgepland1")
            .feitelijkeeinddatum("feitelijkeeinddatum1")
            .jaar("jaar1")
            .periodiciteit("periodiciteit1")
            .verplichtingsoort("verplichtingsoort1");
    }

    public static Verplichtingwmojeugd getVerplichtingwmojeugdSample2() {
        return new Verplichtingwmojeugd()
            .id(2L)
            .budgetsoort("budgetsoort2")
            .budgetsoortgroep("budgetsoortgroep2")
            .einddatumgepland("einddatumgepland2")
            .feitelijkeeinddatum("feitelijkeeinddatum2")
            .jaar("jaar2")
            .periodiciteit("periodiciteit2")
            .verplichtingsoort("verplichtingsoort2");
    }

    public static Verplichtingwmojeugd getVerplichtingwmojeugdRandomSampleGenerator() {
        return new Verplichtingwmojeugd()
            .id(longCount.incrementAndGet())
            .budgetsoort(UUID.randomUUID().toString())
            .budgetsoortgroep(UUID.randomUUID().toString())
            .einddatumgepland(UUID.randomUUID().toString())
            .feitelijkeeinddatum(UUID.randomUUID().toString())
            .jaar(UUID.randomUUID().toString())
            .periodiciteit(UUID.randomUUID().toString())
            .verplichtingsoort(UUID.randomUUID().toString());
    }
}
