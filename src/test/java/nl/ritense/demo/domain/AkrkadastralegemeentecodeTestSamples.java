package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AkrkadastralegemeentecodeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Akrkadastralegemeentecode getAkrkadastralegemeentecodeSample1() {
        return new Akrkadastralegemeentecode()
            .id(1L)
            .akrcode("akrcode1")
            .codeakrkadadastralegemeentecode("codeakrkadadastralegemeentecode1");
    }

    public static Akrkadastralegemeentecode getAkrkadastralegemeentecodeSample2() {
        return new Akrkadastralegemeentecode()
            .id(2L)
            .akrcode("akrcode2")
            .codeakrkadadastralegemeentecode("codeakrkadadastralegemeentecode2");
    }

    public static Akrkadastralegemeentecode getAkrkadastralegemeentecodeRandomSampleGenerator() {
        return new Akrkadastralegemeentecode()
            .id(longCount.incrementAndGet())
            .akrcode(UUID.randomUUID().toString())
            .codeakrkadadastralegemeentecode(UUID.randomUUID().toString());
    }
}
