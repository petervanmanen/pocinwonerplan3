package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NadaanvullingbrpTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Nadaanvullingbrp getNadaanvullingbrpSample1() {
        return new Nadaanvullingbrp().id(1L).opmerkingen("opmerkingen1");
    }

    public static Nadaanvullingbrp getNadaanvullingbrpSample2() {
        return new Nadaanvullingbrp().id(2L).opmerkingen("opmerkingen2");
    }

    public static Nadaanvullingbrp getNadaanvullingbrpRandomSampleGenerator() {
        return new Nadaanvullingbrp().id(longCount.incrementAndGet()).opmerkingen(UUID.randomUUID().toString());
    }
}
