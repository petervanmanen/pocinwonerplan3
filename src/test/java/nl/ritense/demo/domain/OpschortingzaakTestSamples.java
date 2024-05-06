package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OpschortingzaakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Opschortingzaak getOpschortingzaakSample1() {
        return new Opschortingzaak().id(1L).indicatieopschorting("indicatieopschorting1").redenopschorting("redenopschorting1");
    }

    public static Opschortingzaak getOpschortingzaakSample2() {
        return new Opschortingzaak().id(2L).indicatieopschorting("indicatieopschorting2").redenopschorting("redenopschorting2");
    }

    public static Opschortingzaak getOpschortingzaakRandomSampleGenerator() {
        return new Opschortingzaak()
            .id(longCount.incrementAndGet())
            .indicatieopschorting(UUID.randomUUID().toString())
            .redenopschorting(UUID.randomUUID().toString());
    }
}
