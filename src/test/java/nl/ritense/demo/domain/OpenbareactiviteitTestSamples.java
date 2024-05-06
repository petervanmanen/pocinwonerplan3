package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OpenbareactiviteitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Openbareactiviteit getOpenbareactiviteitSample1() {
        return new Openbareactiviteit().id(1L).evenmentnaam("evenmentnaam1").locatieomschrijving("locatieomschrijving1").status("status1");
    }

    public static Openbareactiviteit getOpenbareactiviteitSample2() {
        return new Openbareactiviteit().id(2L).evenmentnaam("evenmentnaam2").locatieomschrijving("locatieomschrijving2").status("status2");
    }

    public static Openbareactiviteit getOpenbareactiviteitRandomSampleGenerator() {
        return new Openbareactiviteit()
            .id(longCount.incrementAndGet())
            .evenmentnaam(UUID.randomUUID().toString())
            .locatieomschrijving(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString());
    }
}
