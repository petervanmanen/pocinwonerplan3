package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TaalniveauTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Taalniveau getTaalniveauSample1() {
        return new Taalniveau().id(1L).mondeling("mondeling1").schriftelijk("schriftelijk1");
    }

    public static Taalniveau getTaalniveauSample2() {
        return new Taalniveau().id(2L).mondeling("mondeling2").schriftelijk("schriftelijk2");
    }

    public static Taalniveau getTaalniveauRandomSampleGenerator() {
        return new Taalniveau()
            .id(longCount.incrementAndGet())
            .mondeling(UUID.randomUUID().toString())
            .schriftelijk(UUID.randomUUID().toString());
    }
}
