package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ZaalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Zaal getZaalSample1() {
        return new Zaal().id(1L).capaciteit("capaciteit1").naam("naam1").nummer("nummer1").omschrijving("omschrijving1");
    }

    public static Zaal getZaalSample2() {
        return new Zaal().id(2L).capaciteit("capaciteit2").naam("naam2").nummer("nummer2").omschrijving("omschrijving2");
    }

    public static Zaal getZaalRandomSampleGenerator() {
        return new Zaal()
            .id(longCount.incrementAndGet())
            .capaciteit(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .nummer(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
