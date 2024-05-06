package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VergaderingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vergadering getVergaderingSample1() {
        return new Vergadering().id(1L).eindtijd("eindtijd1").locatie("locatie1").starttijd("starttijd1").titel("titel1");
    }

    public static Vergadering getVergaderingSample2() {
        return new Vergadering().id(2L).eindtijd("eindtijd2").locatie("locatie2").starttijd("starttijd2").titel("titel2");
    }

    public static Vergadering getVergaderingRandomSampleGenerator() {
        return new Vergadering()
            .id(longCount.incrementAndGet())
            .eindtijd(UUID.randomUUID().toString())
            .locatie(UUID.randomUUID().toString())
            .starttijd(UUID.randomUUID().toString())
            .titel(UUID.randomUUID().toString());
    }
}
