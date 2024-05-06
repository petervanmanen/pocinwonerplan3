package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FormuliersoortveldTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Formuliersoortveld getFormuliersoortveldSample1() {
        return new Formuliersoortveld()
            .id(1L)
            .helptekst("helptekst1")
            .label("label1")
            .maxlengte("maxlengte1")
            .veldnaam("veldnaam1")
            .veldtype("veldtype1");
    }

    public static Formuliersoortveld getFormuliersoortveldSample2() {
        return new Formuliersoortveld()
            .id(2L)
            .helptekst("helptekst2")
            .label("label2")
            .maxlengte("maxlengte2")
            .veldnaam("veldnaam2")
            .veldtype("veldtype2");
    }

    public static Formuliersoortveld getFormuliersoortveldRandomSampleGenerator() {
        return new Formuliersoortveld()
            .id(longCount.incrementAndGet())
            .helptekst(UUID.randomUUID().toString())
            .label(UUID.randomUUID().toString())
            .maxlengte(UUID.randomUUID().toString())
            .veldnaam(UUID.randomUUID().toString())
            .veldtype(UUID.randomUUID().toString());
    }
}
