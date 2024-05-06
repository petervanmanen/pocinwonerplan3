package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SportverenigingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sportvereniging getSportverenigingSample1() {
        return new Sportvereniging()
            .id(1L)
            .aantalnormteams("aantalnormteams1")
            .adres("adres1")
            .email("email1")
            .ledenaantal("ledenaantal1")
            .naam("naam1")
            .typesport("typesport1");
    }

    public static Sportvereniging getSportverenigingSample2() {
        return new Sportvereniging()
            .id(2L)
            .aantalnormteams("aantalnormteams2")
            .adres("adres2")
            .email("email2")
            .ledenaantal("ledenaantal2")
            .naam("naam2")
            .typesport("typesport2");
    }

    public static Sportvereniging getSportverenigingRandomSampleGenerator() {
        return new Sportvereniging()
            .id(longCount.incrementAndGet())
            .aantalnormteams(UUID.randomUUID().toString())
            .adres(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .ledenaantal(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .typesport(UUID.randomUUID().toString());
    }
}
