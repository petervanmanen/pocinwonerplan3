package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LocatieonroerendezaakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Locatieonroerendezaak getLocatieonroerendezaakSample1() {
        return new Locatieonroerendezaak()
            .id(1L)
            .adrestype("adrestype1")
            .cultuurcodebebouwd("cultuurcodebebouwd1")
            .geometrie("geometrie1")
            .locatieomschrijving("locatieomschrijving1");
    }

    public static Locatieonroerendezaak getLocatieonroerendezaakSample2() {
        return new Locatieonroerendezaak()
            .id(2L)
            .adrestype("adrestype2")
            .cultuurcodebebouwd("cultuurcodebebouwd2")
            .geometrie("geometrie2")
            .locatieomschrijving("locatieomschrijving2");
    }

    public static Locatieonroerendezaak getLocatieonroerendezaakRandomSampleGenerator() {
        return new Locatieonroerendezaak()
            .id(longCount.incrementAndGet())
            .adrestype(UUID.randomUUID().toString())
            .cultuurcodebebouwd(UUID.randomUUID().toString())
            .geometrie(UUID.randomUUID().toString())
            .locatieomschrijving(UUID.randomUUID().toString());
    }
}
