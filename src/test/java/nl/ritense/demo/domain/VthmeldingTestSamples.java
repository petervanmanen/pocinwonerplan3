package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VthmeldingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vthmelding getVthmeldingSample1() {
        return new Vthmelding()
            .id(1L)
            .activiteit("activiteit1")
            .beoordeling("beoordeling1")
            .datumtijdtot("datumtijdtot1")
            .geseponeerd("geseponeerd1")
            .locatie("locatie1")
            .organisatieonderdeel("organisatieonderdeel1")
            .overtredingscode("overtredingscode1")
            .overtredingsgroep("overtredingsgroep1")
            .referentienummer("referentienummer1")
            .resultaat("resultaat1")
            .soortvthmelding("soortvthmelding1")
            .status("status1")
            .straatnaam("straatnaam1")
            .taaktype("taaktype1")
            .zaaknummer("zaaknummer1");
    }

    public static Vthmelding getVthmeldingSample2() {
        return new Vthmelding()
            .id(2L)
            .activiteit("activiteit2")
            .beoordeling("beoordeling2")
            .datumtijdtot("datumtijdtot2")
            .geseponeerd("geseponeerd2")
            .locatie("locatie2")
            .organisatieonderdeel("organisatieonderdeel2")
            .overtredingscode("overtredingscode2")
            .overtredingsgroep("overtredingsgroep2")
            .referentienummer("referentienummer2")
            .resultaat("resultaat2")
            .soortvthmelding("soortvthmelding2")
            .status("status2")
            .straatnaam("straatnaam2")
            .taaktype("taaktype2")
            .zaaknummer("zaaknummer2");
    }

    public static Vthmelding getVthmeldingRandomSampleGenerator() {
        return new Vthmelding()
            .id(longCount.incrementAndGet())
            .activiteit(UUID.randomUUID().toString())
            .beoordeling(UUID.randomUUID().toString())
            .datumtijdtot(UUID.randomUUID().toString())
            .geseponeerd(UUID.randomUUID().toString())
            .locatie(UUID.randomUUID().toString())
            .organisatieonderdeel(UUID.randomUUID().toString())
            .overtredingscode(UUID.randomUUID().toString())
            .overtredingsgroep(UUID.randomUUID().toString())
            .referentienummer(UUID.randomUUID().toString())
            .resultaat(UUID.randomUUID().toString())
            .soortvthmelding(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .straatnaam(UUID.randomUUID().toString())
            .taaktype(UUID.randomUUID().toString())
            .zaaknummer(UUID.randomUUID().toString());
    }
}
