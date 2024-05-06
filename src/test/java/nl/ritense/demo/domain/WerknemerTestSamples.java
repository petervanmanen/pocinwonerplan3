package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WerknemerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Werknemer getWerknemerSample1() {
        return new Werknemer().id(1L).naam("naam1").voornaam("voornaam1").woonplaats("woonplaats1");
    }

    public static Werknemer getWerknemerSample2() {
        return new Werknemer().id(2L).naam("naam2").voornaam("voornaam2").woonplaats("woonplaats2");
    }

    public static Werknemer getWerknemerRandomSampleGenerator() {
        return new Werknemer()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .voornaam(UUID.randomUUID().toString())
            .woonplaats(UUID.randomUUID().toString());
    }
}
