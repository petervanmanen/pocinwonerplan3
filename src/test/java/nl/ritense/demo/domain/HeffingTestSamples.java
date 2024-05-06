package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HeffingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Heffing getHeffingSample1() {
        return new Heffing()
            .id(1L)
            .bedrag("bedrag1")
            .code("code1")
            .datumindiening("datumindiening1")
            .inrekening("inrekening1")
            .nummer("nummer1")
            .runnummer("runnummer1");
    }

    public static Heffing getHeffingSample2() {
        return new Heffing()
            .id(2L)
            .bedrag("bedrag2")
            .code("code2")
            .datumindiening("datumindiening2")
            .inrekening("inrekening2")
            .nummer("nummer2")
            .runnummer("runnummer2");
    }

    public static Heffing getHeffingRandomSampleGenerator() {
        return new Heffing()
            .id(longCount.incrementAndGet())
            .bedrag(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .datumindiening(UUID.randomUUID().toString())
            .inrekening(UUID.randomUUID().toString())
            .nummer(UUID.randomUUID().toString())
            .runnummer(UUID.randomUUID().toString());
    }
}
