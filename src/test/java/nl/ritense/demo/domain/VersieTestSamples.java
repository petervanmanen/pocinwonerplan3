package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VersieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Versie getVersieSample1() {
        return new Versie().id(1L).aantal("aantal1").licentie("licentie1").status("status1").versienummer("versienummer1");
    }

    public static Versie getVersieSample2() {
        return new Versie().id(2L).aantal("aantal2").licentie("licentie2").status("status2").versienummer("versienummer2");
    }

    public static Versie getVersieRandomSampleGenerator() {
        return new Versie()
            .id(longCount.incrementAndGet())
            .aantal(UUID.randomUUID().toString())
            .licentie(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .versienummer(UUID.randomUUID().toString());
    }
}
