package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MuseumrelatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Museumrelatie getMuseumrelatieSample1() {
        return new Museumrelatie().id(1L).relatiesoort("relatiesoort1");
    }

    public static Museumrelatie getMuseumrelatieSample2() {
        return new Museumrelatie().id(2L).relatiesoort("relatiesoort2");
    }

    public static Museumrelatie getMuseumrelatieRandomSampleGenerator() {
        return new Museumrelatie().id(longCount.incrementAndGet()).relatiesoort(UUID.randomUUID().toString());
    }
}
