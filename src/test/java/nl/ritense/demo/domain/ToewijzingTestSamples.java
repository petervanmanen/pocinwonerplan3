package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ToewijzingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Toewijzing getToewijzingSample1() {
        return new Toewijzing()
            .id(1L)
            .code("code1")
            .commentaar("commentaar1")
            .eenheid("eenheid1")
            .frequentie("frequentie1")
            .omvang("omvang1")
            .redenwijziging("redenwijziging1")
            .toewijzingnummer("toewijzingnummer1")
            .wet("wet1");
    }

    public static Toewijzing getToewijzingSample2() {
        return new Toewijzing()
            .id(2L)
            .code("code2")
            .commentaar("commentaar2")
            .eenheid("eenheid2")
            .frequentie("frequentie2")
            .omvang("omvang2")
            .redenwijziging("redenwijziging2")
            .toewijzingnummer("toewijzingnummer2")
            .wet("wet2");
    }

    public static Toewijzing getToewijzingRandomSampleGenerator() {
        return new Toewijzing()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .commentaar(UUID.randomUUID().toString())
            .eenheid(UUID.randomUUID().toString())
            .frequentie(UUID.randomUUID().toString())
            .omvang(UUID.randomUUID().toString())
            .redenwijziging(UUID.randomUUID().toString())
            .toewijzingnummer(UUID.randomUUID().toString())
            .wet(UUID.randomUUID().toString());
    }
}
