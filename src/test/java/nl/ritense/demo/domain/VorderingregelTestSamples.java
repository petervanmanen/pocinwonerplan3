package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VorderingregelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vorderingregel getVorderingregelSample1() {
        return new Vorderingregel()
            .id(1L)
            .aangemaaktdoor("aangemaaktdoor1")
            .bedragexclbtw("bedragexclbtw1")
            .bedraginclbtw("bedraginclbtw1")
            .btwcategorie("btwcategorie1")
            .gemuteerddoor("gemuteerddoor1")
            .omschrijving("omschrijving1")
            .periodiek("periodiek1")
            .type("type1");
    }

    public static Vorderingregel getVorderingregelSample2() {
        return new Vorderingregel()
            .id(2L)
            .aangemaaktdoor("aangemaaktdoor2")
            .bedragexclbtw("bedragexclbtw2")
            .bedraginclbtw("bedraginclbtw2")
            .btwcategorie("btwcategorie2")
            .gemuteerddoor("gemuteerddoor2")
            .omschrijving("omschrijving2")
            .periodiek("periodiek2")
            .type("type2");
    }

    public static Vorderingregel getVorderingregelRandomSampleGenerator() {
        return new Vorderingregel()
            .id(longCount.incrementAndGet())
            .aangemaaktdoor(UUID.randomUUID().toString())
            .bedragexclbtw(UUID.randomUUID().toString())
            .bedraginclbtw(UUID.randomUUID().toString())
            .btwcategorie(UUID.randomUUID().toString())
            .gemuteerddoor(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .periodiek(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
