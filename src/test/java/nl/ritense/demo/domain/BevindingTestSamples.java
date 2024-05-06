package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BevindingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bevinding getBevindingSample1() {
        return new Bevinding()
            .id(1L)
            .aangemaaktdoor("aangemaaktdoor1")
            .activiteit("activiteit1")
            .controleelement("controleelement1")
            .controleniveau("controleniveau1")
            .diepte("diepte1")
            .fase("fase1")
            .gemuteerddoor("gemuteerddoor1")
            .resultaat("resultaat1")
            .risico("risico1");
    }

    public static Bevinding getBevindingSample2() {
        return new Bevinding()
            .id(2L)
            .aangemaaktdoor("aangemaaktdoor2")
            .activiteit("activiteit2")
            .controleelement("controleelement2")
            .controleniveau("controleniveau2")
            .diepte("diepte2")
            .fase("fase2")
            .gemuteerddoor("gemuteerddoor2")
            .resultaat("resultaat2")
            .risico("risico2");
    }

    public static Bevinding getBevindingRandomSampleGenerator() {
        return new Bevinding()
            .id(longCount.incrementAndGet())
            .aangemaaktdoor(UUID.randomUUID().toString())
            .activiteit(UUID.randomUUID().toString())
            .controleelement(UUID.randomUUID().toString())
            .controleniveau(UUID.randomUUID().toString())
            .diepte(UUID.randomUUID().toString())
            .fase(UUID.randomUUID().toString())
            .gemuteerddoor(UUID.randomUUID().toString())
            .resultaat(UUID.randomUUID().toString())
            .risico(UUID.randomUUID().toString());
    }
}
