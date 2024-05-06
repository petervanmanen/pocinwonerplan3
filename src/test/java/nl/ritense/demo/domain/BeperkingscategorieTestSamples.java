package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BeperkingscategorieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Beperkingscategorie getBeperkingscategorieSample1() {
        return new Beperkingscategorie().id(1L).code("code1").wet("wet1");
    }

    public static Beperkingscategorie getBeperkingscategorieSample2() {
        return new Beperkingscategorie().id(2L).code("code2").wet("wet2");
    }

    public static Beperkingscategorie getBeperkingscategorieRandomSampleGenerator() {
        return new Beperkingscategorie()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .wet(UUID.randomUUID().toString());
    }
}
