package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TariefTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Tarief getTariefSample1() {
        return new Tarief().id(1L).eenheid("eenheid1").wet("wet1");
    }

    public static Tarief getTariefSample2() {
        return new Tarief().id(2L).eenheid("eenheid2").wet("wet2");
    }

    public static Tarief getTariefRandomSampleGenerator() {
        return new Tarief().id(longCount.incrementAndGet()).eenheid(UUID.randomUUID().toString()).wet(UUID.randomUUID().toString());
    }
}
