package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LoopbaanstapTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Loopbaanstap getLoopbaanstapSample1() {
        return new Loopbaanstap().id(1L).klas("klas1").onderwijstype("onderwijstype1").schooljaar("schooljaar1");
    }

    public static Loopbaanstap getLoopbaanstapSample2() {
        return new Loopbaanstap().id(2L).klas("klas2").onderwijstype("onderwijstype2").schooljaar("schooljaar2");
    }

    public static Loopbaanstap getLoopbaanstapRandomSampleGenerator() {
        return new Loopbaanstap()
            .id(longCount.incrementAndGet())
            .klas(UUID.randomUUID().toString())
            .onderwijstype(UUID.randomUUID().toString())
            .schooljaar(UUID.randomUUID().toString());
    }
}
