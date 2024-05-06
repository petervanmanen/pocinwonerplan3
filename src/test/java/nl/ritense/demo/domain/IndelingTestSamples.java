package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IndelingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Indeling getIndelingSample1() {
        return new Indeling().id(1L).indelingsoort("indelingsoort1").naam("naam1").nummer("nummer1").omschrijving("omschrijving1");
    }

    public static Indeling getIndelingSample2() {
        return new Indeling().id(2L).indelingsoort("indelingsoort2").naam("naam2").nummer("nummer2").omschrijving("omschrijving2");
    }

    public static Indeling getIndelingRandomSampleGenerator() {
        return new Indeling()
            .id(longCount.incrementAndGet())
            .indelingsoort(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .nummer(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
