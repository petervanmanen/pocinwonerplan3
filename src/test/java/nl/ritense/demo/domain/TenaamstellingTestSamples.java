package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TenaamstellingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Tenaamstelling getTenaamstellingSample1() {
        return new Tenaamstelling()
            .id(1L)
            .aandeelinrecht("aandeelinrecht1")
            .burgerlijkestaattentijdevanverkrijging("burgerlijkestaattentijdevanverkrijging1")
            .exploitantcode("exploitantcode1")
            .identificatietenaamstelling("identificatietenaamstelling1")
            .verklaringinzakederdenbescherming("verklaringinzakederdenbescherming1")
            .verkregennamenssamenwerkingsverband("verkregennamenssamenwerkingsverband1");
    }

    public static Tenaamstelling getTenaamstellingSample2() {
        return new Tenaamstelling()
            .id(2L)
            .aandeelinrecht("aandeelinrecht2")
            .burgerlijkestaattentijdevanverkrijging("burgerlijkestaattentijdevanverkrijging2")
            .exploitantcode("exploitantcode2")
            .identificatietenaamstelling("identificatietenaamstelling2")
            .verklaringinzakederdenbescherming("verklaringinzakederdenbescherming2")
            .verkregennamenssamenwerkingsverband("verkregennamenssamenwerkingsverband2");
    }

    public static Tenaamstelling getTenaamstellingRandomSampleGenerator() {
        return new Tenaamstelling()
            .id(longCount.incrementAndGet())
            .aandeelinrecht(UUID.randomUUID().toString())
            .burgerlijkestaattentijdevanverkrijging(UUID.randomUUID().toString())
            .exploitantcode(UUID.randomUUID().toString())
            .identificatietenaamstelling(UUID.randomUUID().toString())
            .verklaringinzakederdenbescherming(UUID.randomUUID().toString())
            .verkregennamenssamenwerkingsverband(UUID.randomUUID().toString());
    }
}
