package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InspectieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Inspectie getInspectieSample1() {
        return new Inspectie()
            .id(1L)
            .aangemaaktdoor("aangemaaktdoor1")
            .gemuteerddoor("gemuteerddoor1")
            .inspectietype("inspectietype1")
            .kenmerk("kenmerk1")
            .omschrijving("omschrijving1")
            .opmerkingen("opmerkingen1")
            .status("status1");
    }

    public static Inspectie getInspectieSample2() {
        return new Inspectie()
            .id(2L)
            .aangemaaktdoor("aangemaaktdoor2")
            .gemuteerddoor("gemuteerddoor2")
            .inspectietype("inspectietype2")
            .kenmerk("kenmerk2")
            .omschrijving("omschrijving2")
            .opmerkingen("opmerkingen2")
            .status("status2");
    }

    public static Inspectie getInspectieRandomSampleGenerator() {
        return new Inspectie()
            .id(longCount.incrementAndGet())
            .aangemaaktdoor(UUID.randomUUID().toString())
            .gemuteerddoor(UUID.randomUUID().toString())
            .inspectietype(UUID.randomUUID().toString())
            .kenmerk(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .opmerkingen(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString());
    }
}
