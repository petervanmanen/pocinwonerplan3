package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FinancielesituatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Financielesituatie getFinancielesituatieSample1() {
        return new Financielesituatie().id(1L).datumvastgesteld("datumvastgesteld1").schuld("schuld1");
    }

    public static Financielesituatie getFinancielesituatieSample2() {
        return new Financielesituatie().id(2L).datumvastgesteld("datumvastgesteld2").schuld("schuld2");
    }

    public static Financielesituatie getFinancielesituatieRandomSampleGenerator() {
        return new Financielesituatie()
            .id(longCount.incrementAndGet())
            .datumvastgesteld(UUID.randomUUID().toString())
            .schuld(UUID.randomUUID().toString());
    }
}
