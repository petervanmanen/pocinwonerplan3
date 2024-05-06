package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FormulierverlenginginhuurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Formulierverlenginginhuur getFormulierverlenginginhuurSample1() {
        return new Formulierverlenginginhuur()
            .id(1L)
            .indicatieredeninhuurgewijzigd("indicatieredeninhuurgewijzigd1")
            .indicatieverhogeninkooporder("indicatieverhogeninkooporder1")
            .toelichting("toelichting1");
    }

    public static Formulierverlenginginhuur getFormulierverlenginginhuurSample2() {
        return new Formulierverlenginginhuur()
            .id(2L)
            .indicatieredeninhuurgewijzigd("indicatieredeninhuurgewijzigd2")
            .indicatieverhogeninkooporder("indicatieverhogeninkooporder2")
            .toelichting("toelichting2");
    }

    public static Formulierverlenginginhuur getFormulierverlenginginhuurRandomSampleGenerator() {
        return new Formulierverlenginginhuur()
            .id(longCount.incrementAndGet())
            .indicatieredeninhuurgewijzigd(UUID.randomUUID().toString())
            .indicatieverhogeninkooporder(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString());
    }
}
