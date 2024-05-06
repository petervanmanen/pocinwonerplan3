package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BalieafspraakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Balieafspraak getBalieafspraakSample1() {
        return new Balieafspraak()
            .id(1L)
            .eindtijdgepland("eindtijdgepland1")
            .notitie("notitie1")
            .starttijdgepland("starttijdgepland1")
            .tijdaangemaakt("tijdaangemaakt1")
            .tijdsduurgepland("tijdsduurgepland1")
            .toelichting("toelichting1")
            .wachttijdnastartafspraak("wachttijdnastartafspraak1")
            .wachttijdtotaal("wachttijdtotaal1")
            .wachttijdvoorstartafspraak("wachttijdvoorstartafspraak1")
            .werkelijketijdsduur("werkelijketijdsduur1");
    }

    public static Balieafspraak getBalieafspraakSample2() {
        return new Balieafspraak()
            .id(2L)
            .eindtijdgepland("eindtijdgepland2")
            .notitie("notitie2")
            .starttijdgepland("starttijdgepland2")
            .tijdaangemaakt("tijdaangemaakt2")
            .tijdsduurgepland("tijdsduurgepland2")
            .toelichting("toelichting2")
            .wachttijdnastartafspraak("wachttijdnastartafspraak2")
            .wachttijdtotaal("wachttijdtotaal2")
            .wachttijdvoorstartafspraak("wachttijdvoorstartafspraak2")
            .werkelijketijdsduur("werkelijketijdsduur2");
    }

    public static Balieafspraak getBalieafspraakRandomSampleGenerator() {
        return new Balieafspraak()
            .id(longCount.incrementAndGet())
            .eindtijdgepland(UUID.randomUUID().toString())
            .notitie(UUID.randomUUID().toString())
            .starttijdgepland(UUID.randomUUID().toString())
            .tijdaangemaakt(UUID.randomUUID().toString())
            .tijdsduurgepland(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString())
            .wachttijdnastartafspraak(UUID.randomUUID().toString())
            .wachttijdtotaal(UUID.randomUUID().toString())
            .wachttijdvoorstartafspraak(UUID.randomUUID().toString())
            .werkelijketijdsduur(UUID.randomUUID().toString());
    }
}
