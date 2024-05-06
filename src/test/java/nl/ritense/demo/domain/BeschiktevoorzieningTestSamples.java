package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BeschiktevoorzieningTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Beschiktevoorziening getBeschiktevoorzieningSample1() {
        return new Beschiktevoorziening()
            .id(1L)
            .code("code1")
            .eenheid("eenheid1")
            .frequentie("frequentie1")
            .leveringsvorm("leveringsvorm1")
            .omvang("omvang1")
            .redeneinde("redeneinde1")
            .status("status1")
            .wet("wet1");
    }

    public static Beschiktevoorziening getBeschiktevoorzieningSample2() {
        return new Beschiktevoorziening()
            .id(2L)
            .code("code2")
            .eenheid("eenheid2")
            .frequentie("frequentie2")
            .leveringsvorm("leveringsvorm2")
            .omvang("omvang2")
            .redeneinde("redeneinde2")
            .status("status2")
            .wet("wet2");
    }

    public static Beschiktevoorziening getBeschiktevoorzieningRandomSampleGenerator() {
        return new Beschiktevoorziening()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .eenheid(UUID.randomUUID().toString())
            .frequentie(UUID.randomUUID().toString())
            .leveringsvorm(UUID.randomUUID().toString())
            .omvang(UUID.randomUUID().toString())
            .redeneinde(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .wet(UUID.randomUUID().toString());
    }
}
