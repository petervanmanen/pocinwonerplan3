package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class KwaliteitscatalogusopenbareruimteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kwaliteitscatalogusopenbareruimte getKwaliteitscatalogusopenbareruimteSample1() {
        return new Kwaliteitscatalogusopenbareruimte().id(1L);
    }

    public static Kwaliteitscatalogusopenbareruimte getKwaliteitscatalogusopenbareruimteSample2() {
        return new Kwaliteitscatalogusopenbareruimte().id(2L);
    }

    public static Kwaliteitscatalogusopenbareruimte getKwaliteitscatalogusopenbareruimteRandomSampleGenerator() {
        return new Kwaliteitscatalogusopenbareruimte().id(longCount.incrementAndGet());
    }
}
