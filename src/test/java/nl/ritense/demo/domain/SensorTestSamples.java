package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SensorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sensor getSensorSample1() {
        return new Sensor()
            .id(1L)
            .aanleghoogte("aanleghoogte1")
            .elektrakast("elektrakast1")
            .frequentieomvormer("frequentieomvormer1")
            .hoogte("hoogte1")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd1")
            .leverancier("leverancier1")
            .meetpunt("meetpunt1")
            .plc("plc1");
    }

    public static Sensor getSensorSample2() {
        return new Sensor()
            .id(2L)
            .aanleghoogte("aanleghoogte2")
            .elektrakast("elektrakast2")
            .frequentieomvormer("frequentieomvormer2")
            .hoogte("hoogte2")
            .jaaronderhouduitgevoerd("jaaronderhouduitgevoerd2")
            .leverancier("leverancier2")
            .meetpunt("meetpunt2")
            .plc("plc2");
    }

    public static Sensor getSensorRandomSampleGenerator() {
        return new Sensor()
            .id(longCount.incrementAndGet())
            .aanleghoogte(UUID.randomUUID().toString())
            .elektrakast(UUID.randomUUID().toString())
            .frequentieomvormer(UUID.randomUUID().toString())
            .hoogte(UUID.randomUUID().toString())
            .jaaronderhouduitgevoerd(UUID.randomUUID().toString())
            .leverancier(UUID.randomUUID().toString())
            .meetpunt(UUID.randomUUID().toString())
            .plc(UUID.randomUUID().toString());
    }
}
