package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KeuzebudgetbestedingsoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Keuzebudgetbestedingsoort getKeuzebudgetbestedingsoortSample1() {
        return new Keuzebudgetbestedingsoort().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Keuzebudgetbestedingsoort getKeuzebudgetbestedingsoortSample2() {
        return new Keuzebudgetbestedingsoort().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Keuzebudgetbestedingsoort getKeuzebudgetbestedingsoortRandomSampleGenerator() {
        return new Keuzebudgetbestedingsoort()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
