package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SpeelterreinTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Speelterrein getSpeelterreinSample1() {
        return new Speelterrein()
            .id(1L)
            .jaarherinrichting("jaarherinrichting1")
            .speelterreinleeftijddoelgroep("speelterreinleeftijddoelgroep1")
            .type("type1")
            .typeplus("typeplus1");
    }

    public static Speelterrein getSpeelterreinSample2() {
        return new Speelterrein()
            .id(2L)
            .jaarherinrichting("jaarherinrichting2")
            .speelterreinleeftijddoelgroep("speelterreinleeftijddoelgroep2")
            .type("type2")
            .typeplus("typeplus2");
    }

    public static Speelterrein getSpeelterreinRandomSampleGenerator() {
        return new Speelterrein()
            .id(longCount.incrementAndGet())
            .jaarherinrichting(UUID.randomUUID().toString())
            .speelterreinleeftijddoelgroep(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .typeplus(UUID.randomUUID().toString());
    }
}
