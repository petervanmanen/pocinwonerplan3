package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GeboorteingeschrevennatuurlijkpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Geboorteingeschrevennatuurlijkpersoon getGeboorteingeschrevennatuurlijkpersoonSample1() {
        return new Geboorteingeschrevennatuurlijkpersoon()
            .id(1L)
            .buitenlandseplaatsgeboorte("buitenlandseplaatsgeboorte1")
            .buitenlandseregiogeboorte("buitenlandseregiogeboorte1")
            .datumgeboorte("datumgeboorte1")
            .gemeentegeboorte("gemeentegeboorte1")
            .landofgebiedgeboorte("landofgebiedgeboorte1")
            .omschrijvinglocatiegeboorte("omschrijvinglocatiegeboorte1");
    }

    public static Geboorteingeschrevennatuurlijkpersoon getGeboorteingeschrevennatuurlijkpersoonSample2() {
        return new Geboorteingeschrevennatuurlijkpersoon()
            .id(2L)
            .buitenlandseplaatsgeboorte("buitenlandseplaatsgeboorte2")
            .buitenlandseregiogeboorte("buitenlandseregiogeboorte2")
            .datumgeboorte("datumgeboorte2")
            .gemeentegeboorte("gemeentegeboorte2")
            .landofgebiedgeboorte("landofgebiedgeboorte2")
            .omschrijvinglocatiegeboorte("omschrijvinglocatiegeboorte2");
    }

    public static Geboorteingeschrevennatuurlijkpersoon getGeboorteingeschrevennatuurlijkpersoonRandomSampleGenerator() {
        return new Geboorteingeschrevennatuurlijkpersoon()
            .id(longCount.incrementAndGet())
            .buitenlandseplaatsgeboorte(UUID.randomUUID().toString())
            .buitenlandseregiogeboorte(UUID.randomUUID().toString())
            .datumgeboorte(UUID.randomUUID().toString())
            .gemeentegeboorte(UUID.randomUUID().toString())
            .landofgebiedgeboorte(UUID.randomUUID().toString())
            .omschrijvinglocatiegeboorte(UUID.randomUUID().toString());
    }
}
