package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HistorischerolTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Historischerol getHistorischerolSample1() {
        return new Historischerol().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Historischerol getHistorischerolSample2() {
        return new Historischerol().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Historischerol getHistorischerolRandomSampleGenerator() {
        return new Historischerol()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
