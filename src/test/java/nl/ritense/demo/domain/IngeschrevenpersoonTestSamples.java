package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IngeschrevenpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ingeschrevenpersoon getIngeschrevenpersoonSample1() {
        return new Ingeschrevenpersoon()
            .id(1L)
            .adresherkomst("adresherkomst1")
            .anummer("anummer1")
            .beschrijvinglocatie("beschrijvinglocatie1")
            .buitenlandsreisdocument("buitenlandsreisdocument1")
            .burgerlijkestaat("burgerlijkestaat1")
            .datumbegingeldigheidverblijfplaats("datumbegingeldigheidverblijfplaats1")
            .datuminschrijvinggemeente("datuminschrijvinggemeente1")
            .datumopschortingbijhouding("datumopschortingbijhouding1")
            .datumvertrekuitnederland("datumvertrekuitnederland1")
            .datumvestigingnederland("datumvestigingnederland1")
            .gemeentevaninschrijving("gemeentevaninschrijving1")
            .gezinsrelatie("gezinsrelatie1")
            .indicatiegeheim("indicatiegeheim1")
            .ingezetene("ingezetene1")
            .landwaarnaarvertrokken("landwaarnaarvertrokken1")
            .landwaarvandaaningeschreven("landwaarvandaaningeschreven1")
            .ouder1("ouder11")
            .ouder2("ouder21")
            .partnerid("partnerid1")
            .redeneindebewoning("redeneindebewoning1")
            .redenopschortingbijhouding("redenopschortingbijhouding1")
            .signaleringreisdocument("signaleringreisdocument1")
            .verblijfstitel("verblijfstitel1");
    }

    public static Ingeschrevenpersoon getIngeschrevenpersoonSample2() {
        return new Ingeschrevenpersoon()
            .id(2L)
            .adresherkomst("adresherkomst2")
            .anummer("anummer2")
            .beschrijvinglocatie("beschrijvinglocatie2")
            .buitenlandsreisdocument("buitenlandsreisdocument2")
            .burgerlijkestaat("burgerlijkestaat2")
            .datumbegingeldigheidverblijfplaats("datumbegingeldigheidverblijfplaats2")
            .datuminschrijvinggemeente("datuminschrijvinggemeente2")
            .datumopschortingbijhouding("datumopschortingbijhouding2")
            .datumvertrekuitnederland("datumvertrekuitnederland2")
            .datumvestigingnederland("datumvestigingnederland2")
            .gemeentevaninschrijving("gemeentevaninschrijving2")
            .gezinsrelatie("gezinsrelatie2")
            .indicatiegeheim("indicatiegeheim2")
            .ingezetene("ingezetene2")
            .landwaarnaarvertrokken("landwaarnaarvertrokken2")
            .landwaarvandaaningeschreven("landwaarvandaaningeschreven2")
            .ouder1("ouder12")
            .ouder2("ouder22")
            .partnerid("partnerid2")
            .redeneindebewoning("redeneindebewoning2")
            .redenopschortingbijhouding("redenopschortingbijhouding2")
            .signaleringreisdocument("signaleringreisdocument2")
            .verblijfstitel("verblijfstitel2");
    }

    public static Ingeschrevenpersoon getIngeschrevenpersoonRandomSampleGenerator() {
        return new Ingeschrevenpersoon()
            .id(longCount.incrementAndGet())
            .adresherkomst(UUID.randomUUID().toString())
            .anummer(UUID.randomUUID().toString())
            .beschrijvinglocatie(UUID.randomUUID().toString())
            .buitenlandsreisdocument(UUID.randomUUID().toString())
            .burgerlijkestaat(UUID.randomUUID().toString())
            .datumbegingeldigheidverblijfplaats(UUID.randomUUID().toString())
            .datuminschrijvinggemeente(UUID.randomUUID().toString())
            .datumopschortingbijhouding(UUID.randomUUID().toString())
            .datumvertrekuitnederland(UUID.randomUUID().toString())
            .datumvestigingnederland(UUID.randomUUID().toString())
            .gemeentevaninschrijving(UUID.randomUUID().toString())
            .gezinsrelatie(UUID.randomUUID().toString())
            .indicatiegeheim(UUID.randomUUID().toString())
            .ingezetene(UUID.randomUUID().toString())
            .landwaarnaarvertrokken(UUID.randomUUID().toString())
            .landwaarvandaaningeschreven(UUID.randomUUID().toString())
            .ouder1(UUID.randomUUID().toString())
            .ouder2(UUID.randomUUID().toString())
            .partnerid(UUID.randomUUID().toString())
            .redeneindebewoning(UUID.randomUUID().toString())
            .redenopschortingbijhouding(UUID.randomUUID().toString())
            .signaleringreisdocument(UUID.randomUUID().toString())
            .verblijfstitel(UUID.randomUUID().toString());
    }
}
