package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ParkeerscanTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Parkeerscan getParkeerscanSample1() {
        return new Parkeerscan()
            .id(1L)
            .codegebruiker("codegebruiker1")
            .codescanvoertuig("codescanvoertuig1")
            .coordinaten("coordinaten1")
            .foto("foto1")
            .kenteken("kenteken1")
            .tijdstip("tijdstip1")
            .transactieid("transactieid1");
    }

    public static Parkeerscan getParkeerscanSample2() {
        return new Parkeerscan()
            .id(2L)
            .codegebruiker("codegebruiker2")
            .codescanvoertuig("codescanvoertuig2")
            .coordinaten("coordinaten2")
            .foto("foto2")
            .kenteken("kenteken2")
            .tijdstip("tijdstip2")
            .transactieid("transactieid2");
    }

    public static Parkeerscan getParkeerscanRandomSampleGenerator() {
        return new Parkeerscan()
            .id(longCount.incrementAndGet())
            .codegebruiker(UUID.randomUUID().toString())
            .codescanvoertuig(UUID.randomUUID().toString())
            .coordinaten(UUID.randomUUID().toString())
            .foto(UUID.randomUUID().toString())
            .kenteken(UUID.randomUUID().toString())
            .tijdstip(UUID.randomUUID().toString())
            .transactieid(UUID.randomUUID().toString());
    }
}
