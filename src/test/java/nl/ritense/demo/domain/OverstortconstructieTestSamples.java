package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OverstortconstructieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Overstortconstructie getOverstortconstructieSample1() {
        return new Overstortconstructie()
            .id(1L)
            .bassin("bassin1")
            .drempelbreedte("drempelbreedte1")
            .drempelniveau("drempelniveau1")
            .type("type1")
            .vormdrempel("vormdrempel1")
            .waking("waking1");
    }

    public static Overstortconstructie getOverstortconstructieSample2() {
        return new Overstortconstructie()
            .id(2L)
            .bassin("bassin2")
            .drempelbreedte("drempelbreedte2")
            .drempelniveau("drempelniveau2")
            .type("type2")
            .vormdrempel("vormdrempel2")
            .waking("waking2");
    }

    public static Overstortconstructie getOverstortconstructieRandomSampleGenerator() {
        return new Overstortconstructie()
            .id(longCount.incrementAndGet())
            .bassin(UUID.randomUUID().toString())
            .drempelbreedte(UUID.randomUUID().toString())
            .drempelniveau(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .vormdrempel(UUID.randomUUID().toString())
            .waking(UUID.randomUUID().toString());
    }
}
