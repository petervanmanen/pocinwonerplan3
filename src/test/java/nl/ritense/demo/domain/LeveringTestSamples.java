package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LeveringTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Levering getLeveringSample1() {
        return new Levering().id(1L).code("code1").eenheid("eenheid1").frequentie("frequentie1").omvang("omvang1").stopreden("stopreden1");
    }

    public static Levering getLeveringSample2() {
        return new Levering().id(2L).code("code2").eenheid("eenheid2").frequentie("frequentie2").omvang("omvang2").stopreden("stopreden2");
    }

    public static Levering getLeveringRandomSampleGenerator() {
        return new Levering()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .eenheid(UUID.randomUUID().toString())
            .frequentie(UUID.randomUUID().toString())
            .omvang(UUID.randomUUID().toString())
            .stopreden(UUID.randomUUID().toString());
    }
}
