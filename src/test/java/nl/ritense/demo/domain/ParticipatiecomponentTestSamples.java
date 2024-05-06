package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ParticipatiecomponentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Participatiecomponent getParticipatiecomponentSample1() {
        return new Participatiecomponent().id(1L);
    }

    public static Participatiecomponent getParticipatiecomponentSample2() {
        return new Participatiecomponent().id(2L);
    }

    public static Participatiecomponent getParticipatiecomponentRandomSampleGenerator() {
        return new Participatiecomponent().id(longCount.incrementAndGet());
    }
}
