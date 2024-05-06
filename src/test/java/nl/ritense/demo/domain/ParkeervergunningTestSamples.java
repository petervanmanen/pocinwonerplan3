package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ParkeervergunningTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Parkeervergunning getParkeervergunningSample1() {
        return new Parkeervergunning()
            .id(1L)
            .datumeindegeldigheid("datumeindegeldigheid1")
            .datumstart("datumstart1")
            .kenteken("kenteken1")
            .minutenafgeschreven("minutenafgeschreven1")
            .minutengeldig("minutengeldig1")
            .minutenresterend("minutenresterend1")
            .nummer("nummer1")
            .type("type1");
    }

    public static Parkeervergunning getParkeervergunningSample2() {
        return new Parkeervergunning()
            .id(2L)
            .datumeindegeldigheid("datumeindegeldigheid2")
            .datumstart("datumstart2")
            .kenteken("kenteken2")
            .minutenafgeschreven("minutenafgeschreven2")
            .minutengeldig("minutengeldig2")
            .minutenresterend("minutenresterend2")
            .nummer("nummer2")
            .type("type2");
    }

    public static Parkeervergunning getParkeervergunningRandomSampleGenerator() {
        return new Parkeervergunning()
            .id(longCount.incrementAndGet())
            .datumeindegeldigheid(UUID.randomUUID().toString())
            .datumstart(UUID.randomUUID().toString())
            .kenteken(UUID.randomUUID().toString())
            .minutenafgeschreven(UUID.randomUUID().toString())
            .minutengeldig(UUID.randomUUID().toString())
            .minutenresterend(UUID.randomUUID().toString())
            .nummer(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
