package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AsielstatushouderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Asielstatushouder getAsielstatushouderSample1() {
        return new Asielstatushouder()
            .id(1L)
            .digidaangevraagd("digidaangevraagd1")
            .emailadresverblijfazc("emailadresverblijfazc1")
            .isgekoppeldaan("isgekoppeldaan1")
            .landrijbewijs("landrijbewijs1")
            .rijbewijs("rijbewijs1")
            .telefoonnummerverblijfazc("telefoonnummerverblijfazc1");
    }

    public static Asielstatushouder getAsielstatushouderSample2() {
        return new Asielstatushouder()
            .id(2L)
            .digidaangevraagd("digidaangevraagd2")
            .emailadresverblijfazc("emailadresverblijfazc2")
            .isgekoppeldaan("isgekoppeldaan2")
            .landrijbewijs("landrijbewijs2")
            .rijbewijs("rijbewijs2")
            .telefoonnummerverblijfazc("telefoonnummerverblijfazc2");
    }

    public static Asielstatushouder getAsielstatushouderRandomSampleGenerator() {
        return new Asielstatushouder()
            .id(longCount.incrementAndGet())
            .digidaangevraagd(UUID.randomUUID().toString())
            .emailadresverblijfazc(UUID.randomUUID().toString())
            .isgekoppeldaan(UUID.randomUUID().toString())
            .landrijbewijs(UUID.randomUUID().toString())
            .rijbewijs(UUID.randomUUID().toString())
            .telefoonnummerverblijfazc(UUID.randomUUID().toString());
    }
}
