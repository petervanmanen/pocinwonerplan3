package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SelectietabelaanbestedingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Selectietabelaanbesteding getSelectietabelaanbestedingSample1() {
        return new Selectietabelaanbesteding()
            .id(1L)
            .aanbestedingsoort("aanbestedingsoort1")
            .opdrachtcategorie("opdrachtcategorie1")
            .openbaar("openbaar1");
    }

    public static Selectietabelaanbesteding getSelectietabelaanbestedingSample2() {
        return new Selectietabelaanbesteding()
            .id(2L)
            .aanbestedingsoort("aanbestedingsoort2")
            .opdrachtcategorie("opdrachtcategorie2")
            .openbaar("openbaar2");
    }

    public static Selectietabelaanbesteding getSelectietabelaanbestedingRandomSampleGenerator() {
        return new Selectietabelaanbesteding()
            .id(longCount.incrementAndGet())
            .aanbestedingsoort(UUID.randomUUID().toString())
            .opdrachtcategorie(UUID.randomUUID().toString())
            .openbaar(UUID.randomUUID().toString());
    }
}
