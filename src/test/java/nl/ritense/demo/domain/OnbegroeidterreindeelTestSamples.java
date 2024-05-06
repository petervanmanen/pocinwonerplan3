package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OnbegroeidterreindeelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Onbegroeidterreindeel getOnbegroeidterreindeelSample1() {
        return new Onbegroeidterreindeel()
            .id(1L)
            .fysiekvoorkomenonbegroeidterreindeel("fysiekvoorkomenonbegroeidterreindeel1")
            .geometrieonbegroeidterreindeel("geometrieonbegroeidterreindeel1")
            .identificatieonbegroeidterreindeel("identificatieonbegroeidterreindeel1")
            .kruinlijngeometrieonbegroeidterreindeel("kruinlijngeometrieonbegroeidterreindeel1")
            .onbegroeidterreindeeloptalud("onbegroeidterreindeeloptalud1")
            .plusfysiekvoorkomenonbegroeidterreindeel("plusfysiekvoorkomenonbegroeidterreindeel1")
            .relatievehoogteliggingonbegroeidterreindeel("relatievehoogteliggingonbegroeidterreindeel1")
            .statusonbegroeidterreindeel("statusonbegroeidterreindeel1");
    }

    public static Onbegroeidterreindeel getOnbegroeidterreindeelSample2() {
        return new Onbegroeidterreindeel()
            .id(2L)
            .fysiekvoorkomenonbegroeidterreindeel("fysiekvoorkomenonbegroeidterreindeel2")
            .geometrieonbegroeidterreindeel("geometrieonbegroeidterreindeel2")
            .identificatieonbegroeidterreindeel("identificatieonbegroeidterreindeel2")
            .kruinlijngeometrieonbegroeidterreindeel("kruinlijngeometrieonbegroeidterreindeel2")
            .onbegroeidterreindeeloptalud("onbegroeidterreindeeloptalud2")
            .plusfysiekvoorkomenonbegroeidterreindeel("plusfysiekvoorkomenonbegroeidterreindeel2")
            .relatievehoogteliggingonbegroeidterreindeel("relatievehoogteliggingonbegroeidterreindeel2")
            .statusonbegroeidterreindeel("statusonbegroeidterreindeel2");
    }

    public static Onbegroeidterreindeel getOnbegroeidterreindeelRandomSampleGenerator() {
        return new Onbegroeidterreindeel()
            .id(longCount.incrementAndGet())
            .fysiekvoorkomenonbegroeidterreindeel(UUID.randomUUID().toString())
            .geometrieonbegroeidterreindeel(UUID.randomUUID().toString())
            .identificatieonbegroeidterreindeel(UUID.randomUUID().toString())
            .kruinlijngeometrieonbegroeidterreindeel(UUID.randomUUID().toString())
            .onbegroeidterreindeeloptalud(UUID.randomUUID().toString())
            .plusfysiekvoorkomenonbegroeidterreindeel(UUID.randomUUID().toString())
            .relatievehoogteliggingonbegroeidterreindeel(UUID.randomUUID().toString())
            .statusonbegroeidterreindeel(UUID.randomUUID().toString());
    }
}
