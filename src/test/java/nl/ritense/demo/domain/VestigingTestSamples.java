package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VestigingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vestiging getVestigingSample1() {
        return new Vestiging()
            .id(1L)
            .commercielevestiging("commercielevestiging1")
            .fulltimewerkzamemannen("fulltimewerkzamemannen1")
            .fulltimewerkzamevrouwen("fulltimewerkzamevrouwen1")
            .handelsnaam("handelsnaam1")
            .parttimewerkzamemannen("parttimewerkzamemannen1")
            .parttimewerkzamevrouwen("parttimewerkzamevrouwen1")
            .toevoegingadres("toevoegingadres1")
            .totaalwerkzamepersonen("totaalwerkzamepersonen1")
            .verkortenaam("verkortenaam1")
            .vestigingsnummer("vestigingsnummer1");
    }

    public static Vestiging getVestigingSample2() {
        return new Vestiging()
            .id(2L)
            .commercielevestiging("commercielevestiging2")
            .fulltimewerkzamemannen("fulltimewerkzamemannen2")
            .fulltimewerkzamevrouwen("fulltimewerkzamevrouwen2")
            .handelsnaam("handelsnaam2")
            .parttimewerkzamemannen("parttimewerkzamemannen2")
            .parttimewerkzamevrouwen("parttimewerkzamevrouwen2")
            .toevoegingadres("toevoegingadres2")
            .totaalwerkzamepersonen("totaalwerkzamepersonen2")
            .verkortenaam("verkortenaam2")
            .vestigingsnummer("vestigingsnummer2");
    }

    public static Vestiging getVestigingRandomSampleGenerator() {
        return new Vestiging()
            .id(longCount.incrementAndGet())
            .commercielevestiging(UUID.randomUUID().toString())
            .fulltimewerkzamemannen(UUID.randomUUID().toString())
            .fulltimewerkzamevrouwen(UUID.randomUUID().toString())
            .handelsnaam(UUID.randomUUID().toString())
            .parttimewerkzamemannen(UUID.randomUUID().toString())
            .parttimewerkzamevrouwen(UUID.randomUUID().toString())
            .toevoegingadres(UUID.randomUUID().toString())
            .totaalwerkzamepersonen(UUID.randomUUID().toString())
            .verkortenaam(UUID.randomUUID().toString())
            .vestigingsnummer(UUID.randomUUID().toString());
    }
}
