package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InkomensvoorzieningsoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Inkomensvoorzieningsoort getInkomensvoorzieningsoortSample1() {
        return new Inkomensvoorzieningsoort()
            .id(1L)
            .code("code1")
            .naam("naam1")
            .omschrijving("omschrijving1")
            .regeling("regeling1")
            .regelingcode("regelingcode1")
            .vergoeding("vergoeding1")
            .vergoedingscode("vergoedingscode1")
            .wet("wet1");
    }

    public static Inkomensvoorzieningsoort getInkomensvoorzieningsoortSample2() {
        return new Inkomensvoorzieningsoort()
            .id(2L)
            .code("code2")
            .naam("naam2")
            .omschrijving("omschrijving2")
            .regeling("regeling2")
            .regelingcode("regelingcode2")
            .vergoeding("vergoeding2")
            .vergoedingscode("vergoedingscode2")
            .wet("wet2");
    }

    public static Inkomensvoorzieningsoort getInkomensvoorzieningsoortRandomSampleGenerator() {
        return new Inkomensvoorzieningsoort()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .regeling(UUID.randomUUID().toString())
            .regelingcode(UUID.randomUUID().toString())
            .vergoeding(UUID.randomUUID().toString())
            .vergoedingscode(UUID.randomUUID().toString())
            .wet(UUID.randomUUID().toString());
    }
}
