package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GemeentebegrafenisTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Gemeentebegrafenis getGemeentebegrafenisSample1() {
        return new Gemeentebegrafenis()
            .id(1L)
            .achtergrondmelding("achtergrondmelding1")
            .doodsoorzaak("doodsoorzaak1")
            .inkoopordernummer("inkoopordernummer1")
            .melder("melder1")
            .urengemeente("urengemeente1");
    }

    public static Gemeentebegrafenis getGemeentebegrafenisSample2() {
        return new Gemeentebegrafenis()
            .id(2L)
            .achtergrondmelding("achtergrondmelding2")
            .doodsoorzaak("doodsoorzaak2")
            .inkoopordernummer("inkoopordernummer2")
            .melder("melder2")
            .urengemeente("urengemeente2");
    }

    public static Gemeentebegrafenis getGemeentebegrafenisRandomSampleGenerator() {
        return new Gemeentebegrafenis()
            .id(longCount.incrementAndGet())
            .achtergrondmelding(UUID.randomUUID().toString())
            .doodsoorzaak(UUID.randomUUID().toString())
            .inkoopordernummer(UUID.randomUUID().toString())
            .melder(UUID.randomUUID().toString())
            .urengemeente(UUID.randomUUID().toString());
    }
}
