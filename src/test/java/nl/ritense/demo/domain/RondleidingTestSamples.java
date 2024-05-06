package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RondleidingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Rondleiding getRondleidingSample1() {
        return new Rondleiding().id(1L).eindtijd("eindtijd1").naam("naam1").omschrijving("omschrijving1").starttijd("starttijd1");
    }

    public static Rondleiding getRondleidingSample2() {
        return new Rondleiding().id(2L).eindtijd("eindtijd2").naam("naam2").omschrijving("omschrijving2").starttijd("starttijd2");
    }

    public static Rondleiding getRondleidingRandomSampleGenerator() {
        return new Rondleiding()
            .id(longCount.incrementAndGet())
            .eindtijd(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .starttijd(UUID.randomUUID().toString());
    }
}
