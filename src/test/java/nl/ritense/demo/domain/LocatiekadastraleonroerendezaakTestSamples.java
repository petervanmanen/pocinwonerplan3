package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LocatiekadastraleonroerendezaakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Locatiekadastraleonroerendezaak getLocatiekadastraleonroerendezaakSample1() {
        return new Locatiekadastraleonroerendezaak()
            .id(1L)
            .aardcultuurbebouwd("aardcultuurbebouwd1")
            .locatieomschrijving("locatieomschrijving1");
    }

    public static Locatiekadastraleonroerendezaak getLocatiekadastraleonroerendezaakSample2() {
        return new Locatiekadastraleonroerendezaak()
            .id(2L)
            .aardcultuurbebouwd("aardcultuurbebouwd2")
            .locatieomschrijving("locatieomschrijving2");
    }

    public static Locatiekadastraleonroerendezaak getLocatiekadastraleonroerendezaakRandomSampleGenerator() {
        return new Locatiekadastraleonroerendezaak()
            .id(longCount.incrementAndGet())
            .aardcultuurbebouwd(UUID.randomUUID().toString())
            .locatieomschrijving(UUID.randomUUID().toString());
    }
}
