package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SubsidiecomponentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Subsidiecomponent getSubsidiecomponentSample1() {
        return new Subsidiecomponent().id(1L);
    }

    public static Subsidiecomponent getSubsidiecomponentSample2() {
        return new Subsidiecomponent().id(2L);
    }

    public static Subsidiecomponent getSubsidiecomponentRandomSampleGenerator() {
        return new Subsidiecomponent().id(longCount.incrementAndGet());
    }
}
