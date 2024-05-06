package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SbiactiviteitvestigingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sbiactiviteitvestiging getSbiactiviteitvestigingSample1() {
        return new Sbiactiviteitvestiging().id(1L).indicatiehoofdactiviteit("indicatiehoofdactiviteit1").sbicode("sbicode1");
    }

    public static Sbiactiviteitvestiging getSbiactiviteitvestigingSample2() {
        return new Sbiactiviteitvestiging().id(2L).indicatiehoofdactiviteit("indicatiehoofdactiviteit2").sbicode("sbicode2");
    }

    public static Sbiactiviteitvestiging getSbiactiviteitvestigingRandomSampleGenerator() {
        return new Sbiactiviteitvestiging()
            .id(longCount.incrementAndGet())
            .indicatiehoofdactiviteit(UUID.randomUUID().toString())
            .sbicode(UUID.randomUUID().toString());
    }
}
