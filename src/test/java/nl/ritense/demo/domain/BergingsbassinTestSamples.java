package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BergingsbassinTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bergingsbassin getBergingsbassinSample1() {
        return new Bergingsbassin()
            .id(1L)
            .bergendvermogen("bergendvermogen1")
            .pompledigingsvoorziening("pompledigingsvoorziening1")
            .pompspoelvoorziening("pompspoelvoorziening1")
            .spoelleiding("spoelleiding1")
            .vorm("vorm1");
    }

    public static Bergingsbassin getBergingsbassinSample2() {
        return new Bergingsbassin()
            .id(2L)
            .bergendvermogen("bergendvermogen2")
            .pompledigingsvoorziening("pompledigingsvoorziening2")
            .pompspoelvoorziening("pompspoelvoorziening2")
            .spoelleiding("spoelleiding2")
            .vorm("vorm2");
    }

    public static Bergingsbassin getBergingsbassinRandomSampleGenerator() {
        return new Bergingsbassin()
            .id(longCount.incrementAndGet())
            .bergendvermogen(UUID.randomUUID().toString())
            .pompledigingsvoorziening(UUID.randomUUID().toString())
            .pompspoelvoorziening(UUID.randomUUID().toString())
            .spoelleiding(UUID.randomUUID().toString())
            .vorm(UUID.randomUUID().toString());
    }
}
