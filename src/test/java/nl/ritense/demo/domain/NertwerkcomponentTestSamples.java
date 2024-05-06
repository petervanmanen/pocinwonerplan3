package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class NertwerkcomponentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Nertwerkcomponent getNertwerkcomponentSample1() {
        return new Nertwerkcomponent().id(1L);
    }

    public static Nertwerkcomponent getNertwerkcomponentSample2() {
        return new Nertwerkcomponent().id(2L);
    }

    public static Nertwerkcomponent getNertwerkcomponentRandomSampleGenerator() {
        return new Nertwerkcomponent().id(longCount.incrementAndGet());
    }
}
