package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KadastraleonroerendezaakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kadastraleonroerendezaak getKadastraleonroerendezaakSample1() {
        return new Kadastraleonroerendezaak()
            .id(1L)
            .empty("empty1")
            .appartementsrechtvolgnummer("appartementsrechtvolgnummer1")
            .begrenzing("begrenzing1")
            .cultuurcodeonbebouwd("cultuurcodeonbebouwd1")
            .identificatie("identificatie1")
            .kadastralegemeente("kadastralegemeente1")
            .kadastralegemeentecode("kadastralegemeentecode1")
            .koopjaar("koopjaar1")
            .landinrichtingrenteeindejaar("landinrichtingrenteeindejaar1")
            .ligging("ligging1")
            .locatieomschrijving("locatieomschrijving1")
            .oppervlakte("oppervlakte1")
            .oud("oud1")
            .perceelnummer("perceelnummer1")
            .sectie("sectie1")
            .valutacode("valutacode1");
    }

    public static Kadastraleonroerendezaak getKadastraleonroerendezaakSample2() {
        return new Kadastraleonroerendezaak()
            .id(2L)
            .empty("empty2")
            .appartementsrechtvolgnummer("appartementsrechtvolgnummer2")
            .begrenzing("begrenzing2")
            .cultuurcodeonbebouwd("cultuurcodeonbebouwd2")
            .identificatie("identificatie2")
            .kadastralegemeente("kadastralegemeente2")
            .kadastralegemeentecode("kadastralegemeentecode2")
            .koopjaar("koopjaar2")
            .landinrichtingrenteeindejaar("landinrichtingrenteeindejaar2")
            .ligging("ligging2")
            .locatieomschrijving("locatieomschrijving2")
            .oppervlakte("oppervlakte2")
            .oud("oud2")
            .perceelnummer("perceelnummer2")
            .sectie("sectie2")
            .valutacode("valutacode2");
    }

    public static Kadastraleonroerendezaak getKadastraleonroerendezaakRandomSampleGenerator() {
        return new Kadastraleonroerendezaak()
            .id(longCount.incrementAndGet())
            .empty(UUID.randomUUID().toString())
            .appartementsrechtvolgnummer(UUID.randomUUID().toString())
            .begrenzing(UUID.randomUUID().toString())
            .cultuurcodeonbebouwd(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .kadastralegemeente(UUID.randomUUID().toString())
            .kadastralegemeentecode(UUID.randomUUID().toString())
            .koopjaar(UUID.randomUUID().toString())
            .landinrichtingrenteeindejaar(UUID.randomUUID().toString())
            .ligging(UUID.randomUUID().toString())
            .locatieomschrijving(UUID.randomUUID().toString())
            .oppervlakte(UUID.randomUUID().toString())
            .oud(UUID.randomUUID().toString())
            .perceelnummer(UUID.randomUUID().toString())
            .sectie(UUID.randomUUID().toString())
            .valutacode(UUID.randomUUID().toString());
    }
}
