package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VorderingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vordering getVorderingSample1() {
        return new Vordering()
            .id(1L)
            .aangemaaktdoor("aangemaaktdoor1")
            .bedragbtw("bedragbtw1")
            .geaccordeerd("geaccordeerd1")
            .geaccordeerddoor("geaccordeerddoor1")
            .geexporteerd("geexporteerd1")
            .gemuteerddoor("gemuteerddoor1")
            .omschrijving("omschrijving1")
            .totaalbedrag("totaalbedrag1")
            .totaalbedraginclusief("totaalbedraginclusief1")
            .vorderingnummer("vorderingnummer1");
    }

    public static Vordering getVorderingSample2() {
        return new Vordering()
            .id(2L)
            .aangemaaktdoor("aangemaaktdoor2")
            .bedragbtw("bedragbtw2")
            .geaccordeerd("geaccordeerd2")
            .geaccordeerddoor("geaccordeerddoor2")
            .geexporteerd("geexporteerd2")
            .gemuteerddoor("gemuteerddoor2")
            .omschrijving("omschrijving2")
            .totaalbedrag("totaalbedrag2")
            .totaalbedraginclusief("totaalbedraginclusief2")
            .vorderingnummer("vorderingnummer2");
    }

    public static Vordering getVorderingRandomSampleGenerator() {
        return new Vordering()
            .id(longCount.incrementAndGet())
            .aangemaaktdoor(UUID.randomUUID().toString())
            .bedragbtw(UUID.randomUUID().toString())
            .geaccordeerd(UUID.randomUUID().toString())
            .geaccordeerddoor(UUID.randomUUID().toString())
            .geexporteerd(UUID.randomUUID().toString())
            .gemuteerddoor(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .totaalbedrag(UUID.randomUUID().toString())
            .totaalbedraginclusief(UUID.randomUUID().toString())
            .vorderingnummer(UUID.randomUUID().toString());
    }
}
