package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BinnenlocatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Binnenlocatie getBinnenlocatieSample1() {
        return new Binnenlocatie()
            .id(1L)
            .adres("adres1")
            .bouwjaar("bouwjaar1")
            .gymzaal("gymzaal1")
            .klokurenonderwijs("klokurenonderwijs1")
            .klokurenverenigingen("klokurenverenigingen1")
            .locatie("locatie1")
            .onderhoudsniveau("onderhoudsniveau1")
            .onderhoudsstatus("onderhoudsstatus1")
            .sporthal("sporthal1")
            .vloeroppervlakte("vloeroppervlakte1");
    }

    public static Binnenlocatie getBinnenlocatieSample2() {
        return new Binnenlocatie()
            .id(2L)
            .adres("adres2")
            .bouwjaar("bouwjaar2")
            .gymzaal("gymzaal2")
            .klokurenonderwijs("klokurenonderwijs2")
            .klokurenverenigingen("klokurenverenigingen2")
            .locatie("locatie2")
            .onderhoudsniveau("onderhoudsniveau2")
            .onderhoudsstatus("onderhoudsstatus2")
            .sporthal("sporthal2")
            .vloeroppervlakte("vloeroppervlakte2");
    }

    public static Binnenlocatie getBinnenlocatieRandomSampleGenerator() {
        return new Binnenlocatie()
            .id(longCount.incrementAndGet())
            .adres(UUID.randomUUID().toString())
            .bouwjaar(UUID.randomUUID().toString())
            .gymzaal(UUID.randomUUID().toString())
            .klokurenonderwijs(UUID.randomUUID().toString())
            .klokurenverenigingen(UUID.randomUUID().toString())
            .locatie(UUID.randomUUID().toString())
            .onderhoudsniveau(UUID.randomUUID().toString())
            .onderhoudsstatus(UUID.randomUUID().toString())
            .sporthal(UUID.randomUUID().toString())
            .vloeroppervlakte(UUID.randomUUID().toString());
    }
}
