package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SportterreinTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sportterrein getSportterreinSample1() {
        return new Sportterrein()
            .id(1L)
            .gebruiksvorm("gebruiksvorm1")
            .sportcomplex("sportcomplex1")
            .sportterreintypesport("sportterreintypesport1")
            .type("type1")
            .typeplus("typeplus1")
            .veldnummer("veldnummer1");
    }

    public static Sportterrein getSportterreinSample2() {
        return new Sportterrein()
            .id(2L)
            .gebruiksvorm("gebruiksvorm2")
            .sportcomplex("sportcomplex2")
            .sportterreintypesport("sportterreintypesport2")
            .type("type2")
            .typeplus("typeplus2")
            .veldnummer("veldnummer2");
    }

    public static Sportterrein getSportterreinRandomSampleGenerator() {
        return new Sportterrein()
            .id(longCount.incrementAndGet())
            .gebruiksvorm(UUID.randomUUID().toString())
            .sportcomplex(UUID.randomUUID().toString())
            .sportterreintypesport(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .typeplus(UUID.randomUUID().toString())
            .veldnummer(UUID.randomUUID().toString());
    }
}
