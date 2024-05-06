package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SplitsingstekeningreferentieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Splitsingstekeningreferentie getSplitsingstekeningreferentieSample1() {
        return new Splitsingstekeningreferentie()
            .id(1L)
            .bronorganisatie("bronorganisatie1")
            .identificatietekening("identificatietekening1")
            .titel("titel1");
    }

    public static Splitsingstekeningreferentie getSplitsingstekeningreferentieSample2() {
        return new Splitsingstekeningreferentie()
            .id(2L)
            .bronorganisatie("bronorganisatie2")
            .identificatietekening("identificatietekening2")
            .titel("titel2");
    }

    public static Splitsingstekeningreferentie getSplitsingstekeningreferentieRandomSampleGenerator() {
        return new Splitsingstekeningreferentie()
            .id(longCount.incrementAndGet())
            .bronorganisatie(UUID.randomUUID().toString())
            .identificatietekening(UUID.randomUUID().toString())
            .titel(UUID.randomUUID().toString());
    }
}
