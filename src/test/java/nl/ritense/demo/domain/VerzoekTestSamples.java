package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerzoekTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verzoek getVerzoekSample1() {
        return new Verzoek()
            .id(1L)
            .doel("doel1")
            .naam("naam1")
            .referentieaanvrager("referentieaanvrager1")
            .toelichtinglateraantelevereninformatie("toelichtinglateraantelevereninformatie1")
            .toelichtingnietaantelevereninformatie("toelichtingnietaantelevereninformatie1")
            .toelichtingverzoek("toelichtingverzoek1")
            .type("type1")
            .verzoeknummer("verzoeknummer1")
            .volgnummer("volgnummer1");
    }

    public static Verzoek getVerzoekSample2() {
        return new Verzoek()
            .id(2L)
            .doel("doel2")
            .naam("naam2")
            .referentieaanvrager("referentieaanvrager2")
            .toelichtinglateraantelevereninformatie("toelichtinglateraantelevereninformatie2")
            .toelichtingnietaantelevereninformatie("toelichtingnietaantelevereninformatie2")
            .toelichtingverzoek("toelichtingverzoek2")
            .type("type2")
            .verzoeknummer("verzoeknummer2")
            .volgnummer("volgnummer2");
    }

    public static Verzoek getVerzoekRandomSampleGenerator() {
        return new Verzoek()
            .id(longCount.incrementAndGet())
            .doel(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .referentieaanvrager(UUID.randomUUID().toString())
            .toelichtinglateraantelevereninformatie(UUID.randomUUID().toString())
            .toelichtingnietaantelevereninformatie(UUID.randomUUID().toString())
            .toelichtingverzoek(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .verzoeknummer(UUID.randomUUID().toString())
            .volgnummer(UUID.randomUUID().toString());
    }
}
