package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TelefoontjeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Telefoontje getTelefoontjeSample1() {
        return new Telefoontje()
            .id(1L)
            .afhandeltijdnagesprek("afhandeltijdnagesprek1")
            .deltaisdnconnectie("deltaisdnconnectie1")
            .eindtijd("eindtijd1")
            .starttijd("starttijd1")
            .totaleonholdtijd("totaleonholdtijd1")
            .totalespreektijd("totalespreektijd1")
            .totalewachttijd("totalewachttijd1")
            .totlatetijdsduur("totlatetijdsduur1")
            .trackid("trackid1");
    }

    public static Telefoontje getTelefoontjeSample2() {
        return new Telefoontje()
            .id(2L)
            .afhandeltijdnagesprek("afhandeltijdnagesprek2")
            .deltaisdnconnectie("deltaisdnconnectie2")
            .eindtijd("eindtijd2")
            .starttijd("starttijd2")
            .totaleonholdtijd("totaleonholdtijd2")
            .totalespreektijd("totalespreektijd2")
            .totalewachttijd("totalewachttijd2")
            .totlatetijdsduur("totlatetijdsduur2")
            .trackid("trackid2");
    }

    public static Telefoontje getTelefoontjeRandomSampleGenerator() {
        return new Telefoontje()
            .id(longCount.incrementAndGet())
            .afhandeltijdnagesprek(UUID.randomUUID().toString())
            .deltaisdnconnectie(UUID.randomUUID().toString())
            .eindtijd(UUID.randomUUID().toString())
            .starttijd(UUID.randomUUID().toString())
            .totaleonholdtijd(UUID.randomUUID().toString())
            .totalespreektijd(UUID.randomUUID().toString())
            .totalewachttijd(UUID.randomUUID().toString())
            .totlatetijdsduur(UUID.randomUUID().toString())
            .trackid(UUID.randomUUID().toString());
    }
}
