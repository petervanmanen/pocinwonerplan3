package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ZekerheidsrechtTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Zekerheidsrecht getZekerheidsrechtSample1() {
        return new Zekerheidsrecht()
            .id(1L)
            .aandeelinbetrokkenrecht("aandeelinbetrokkenrecht1")
            .identificatiezekerheidsrecht("identificatiezekerheidsrecht1")
            .omschrijvingbetrokkenrecht("omschrijvingbetrokkenrecht1")
            .typezekerheidsrecht("typezekerheidsrecht1");
    }

    public static Zekerheidsrecht getZekerheidsrechtSample2() {
        return new Zekerheidsrecht()
            .id(2L)
            .aandeelinbetrokkenrecht("aandeelinbetrokkenrecht2")
            .identificatiezekerheidsrecht("identificatiezekerheidsrecht2")
            .omschrijvingbetrokkenrecht("omschrijvingbetrokkenrecht2")
            .typezekerheidsrecht("typezekerheidsrecht2");
    }

    public static Zekerheidsrecht getZekerheidsrechtRandomSampleGenerator() {
        return new Zekerheidsrecht()
            .id(longCount.incrementAndGet())
            .aandeelinbetrokkenrecht(UUID.randomUUID().toString())
            .identificatiezekerheidsrecht(UUID.randomUUID().toString())
            .omschrijvingbetrokkenrecht(UUID.randomUUID().toString())
            .typezekerheidsrecht(UUID.randomUUID().toString());
    }
}
