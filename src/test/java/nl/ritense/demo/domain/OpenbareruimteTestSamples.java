package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OpenbareruimteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Openbareruimte getOpenbareruimteSample1() {
        return new Openbareruimte()
            .id(1L)
            .geometrie("geometrie1")
            .huisnummerrangeevenenonevennummers("huisnummerrangeevenenonevennummers1")
            .huisnummerrangeevennummers("huisnummerrangeevennummers1")
            .huisnummerrangeonevennummers("huisnummerrangeonevennummers1")
            .identificatie("identificatie1")
            .labelnaam("labelnaam1")
            .naamopenbareruimte("naamopenbareruimte1")
            .status("status1")
            .straatcode("straatcode1")
            .straatnaam("straatnaam1")
            .typeopenbareruimte("typeopenbareruimte1")
            .versie("versie1")
            .wegsegment("wegsegment1");
    }

    public static Openbareruimte getOpenbareruimteSample2() {
        return new Openbareruimte()
            .id(2L)
            .geometrie("geometrie2")
            .huisnummerrangeevenenonevennummers("huisnummerrangeevenenonevennummers2")
            .huisnummerrangeevennummers("huisnummerrangeevennummers2")
            .huisnummerrangeonevennummers("huisnummerrangeonevennummers2")
            .identificatie("identificatie2")
            .labelnaam("labelnaam2")
            .naamopenbareruimte("naamopenbareruimte2")
            .status("status2")
            .straatcode("straatcode2")
            .straatnaam("straatnaam2")
            .typeopenbareruimte("typeopenbareruimte2")
            .versie("versie2")
            .wegsegment("wegsegment2");
    }

    public static Openbareruimte getOpenbareruimteRandomSampleGenerator() {
        return new Openbareruimte()
            .id(longCount.incrementAndGet())
            .geometrie(UUID.randomUUID().toString())
            .huisnummerrangeevenenonevennummers(UUID.randomUUID().toString())
            .huisnummerrangeevennummers(UUID.randomUUID().toString())
            .huisnummerrangeonevennummers(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .labelnaam(UUID.randomUUID().toString())
            .naamopenbareruimte(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .straatcode(UUID.randomUUID().toString())
            .straatnaam(UUID.randomUUID().toString())
            .typeopenbareruimte(UUID.randomUUID().toString())
            .versie(UUID.randomUUID().toString())
            .wegsegment(UUID.randomUUID().toString());
    }
}
