package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EcomponentsoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ecomponentsoort getEcomponentsoortSample1() {
        return new Ecomponentsoort()
            .id(1L)
            .ecomponent("ecomponent1")
            .ecomponentcode("ecomponentcode1")
            .kolom("kolom1")
            .kolomcode("kolomcode1")
            .regeling("regeling1")
            .regelingcode("regelingcode1");
    }

    public static Ecomponentsoort getEcomponentsoortSample2() {
        return new Ecomponentsoort()
            .id(2L)
            .ecomponent("ecomponent2")
            .ecomponentcode("ecomponentcode2")
            .kolom("kolom2")
            .kolomcode("kolomcode2")
            .regeling("regeling2")
            .regelingcode("regelingcode2");
    }

    public static Ecomponentsoort getEcomponentsoortRandomSampleGenerator() {
        return new Ecomponentsoort()
            .id(longCount.incrementAndGet())
            .ecomponent(UUID.randomUUID().toString())
            .ecomponentcode(UUID.randomUUID().toString())
            .kolom(UUID.randomUUID().toString())
            .kolomcode(UUID.randomUUID().toString())
            .regeling(UUID.randomUUID().toString())
            .regelingcode(UUID.randomUUID().toString());
    }
}
