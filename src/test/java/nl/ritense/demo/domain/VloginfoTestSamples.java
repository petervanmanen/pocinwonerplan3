package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VloginfoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vloginfo getVloginfoSample1() {
        return new Vloginfo()
            .id(1L)
            .detectieverkeer("detectieverkeer1")
            .snelheid("snelheid1")
            .tijdstip("tijdstip1")
            .wachttijd("wachttijd1");
    }

    public static Vloginfo getVloginfoSample2() {
        return new Vloginfo()
            .id(2L)
            .detectieverkeer("detectieverkeer2")
            .snelheid("snelheid2")
            .tijdstip("tijdstip2")
            .wachttijd("wachttijd2");
    }

    public static Vloginfo getVloginfoRandomSampleGenerator() {
        return new Vloginfo()
            .id(longCount.incrementAndGet())
            .detectieverkeer(UUID.randomUUID().toString())
            .snelheid(UUID.randomUUID().toString())
            .tijdstip(UUID.randomUUID().toString())
            .wachttijd(UUID.randomUUID().toString());
    }
}
