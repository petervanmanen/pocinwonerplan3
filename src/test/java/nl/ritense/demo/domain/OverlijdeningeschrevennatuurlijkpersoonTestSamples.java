package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OverlijdeningeschrevennatuurlijkpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Overlijdeningeschrevennatuurlijkpersoon getOverlijdeningeschrevennatuurlijkpersoonSample1() {
        return new Overlijdeningeschrevennatuurlijkpersoon()
            .id(1L)
            .buitenlandseplaatsoverlijden("buitenlandseplaatsoverlijden1")
            .buitenlandseregiooverlijden("buitenlandseregiooverlijden1")
            .datumoverlijden("datumoverlijden1")
            .gemeenteoverlijden("gemeenteoverlijden1")
            .landofgebiedoverlijden("landofgebiedoverlijden1")
            .omschrijvinglocatieoverlijden("omschrijvinglocatieoverlijden1");
    }

    public static Overlijdeningeschrevennatuurlijkpersoon getOverlijdeningeschrevennatuurlijkpersoonSample2() {
        return new Overlijdeningeschrevennatuurlijkpersoon()
            .id(2L)
            .buitenlandseplaatsoverlijden("buitenlandseplaatsoverlijden2")
            .buitenlandseregiooverlijden("buitenlandseregiooverlijden2")
            .datumoverlijden("datumoverlijden2")
            .gemeenteoverlijden("gemeenteoverlijden2")
            .landofgebiedoverlijden("landofgebiedoverlijden2")
            .omschrijvinglocatieoverlijden("omschrijvinglocatieoverlijden2");
    }

    public static Overlijdeningeschrevennatuurlijkpersoon getOverlijdeningeschrevennatuurlijkpersoonRandomSampleGenerator() {
        return new Overlijdeningeschrevennatuurlijkpersoon()
            .id(longCount.incrementAndGet())
            .buitenlandseplaatsoverlijden(UUID.randomUUID().toString())
            .buitenlandseregiooverlijden(UUID.randomUUID().toString())
            .datumoverlijden(UUID.randomUUID().toString())
            .gemeenteoverlijden(UUID.randomUUID().toString())
            .landofgebiedoverlijden(UUID.randomUUID().toString())
            .omschrijvinglocatieoverlijden(UUID.randomUUID().toString());
    }
}
