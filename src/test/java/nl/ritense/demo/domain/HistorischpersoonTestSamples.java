package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HistorischpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Historischpersoon getHistorischpersoonSample1() {
        return new Historischpersoon()
            .id(1L)
            .beroep("beroep1")
            .naam("naam1")
            .omschrijving("omschrijving1")
            .publiektoegankelijk("publiektoegankelijk1")
            .woondeop("woondeop1");
    }

    public static Historischpersoon getHistorischpersoonSample2() {
        return new Historischpersoon()
            .id(2L)
            .beroep("beroep2")
            .naam("naam2")
            .omschrijving("omschrijving2")
            .publiektoegankelijk("publiektoegankelijk2")
            .woondeop("woondeop2");
    }

    public static Historischpersoon getHistorischpersoonRandomSampleGenerator() {
        return new Historischpersoon()
            .id(longCount.incrementAndGet())
            .beroep(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .publiektoegankelijk(UUID.randomUUID().toString())
            .woondeop(UUID.randomUUID().toString());
    }
}
