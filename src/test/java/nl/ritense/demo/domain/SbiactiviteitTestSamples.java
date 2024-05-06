package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SbiactiviteitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sbiactiviteit getSbiactiviteitSample1() {
        return new Sbiactiviteit()
            .id(1L)
            .hoofdniveau("hoofdniveau1")
            .hoofdniveauomschrijving("hoofdniveauomschrijving1")
            .naamactiviteit("naamactiviteit1")
            .sbicode("sbicode1")
            .sbigroep("sbigroep1")
            .sbigroepomschrijving("sbigroepomschrijving1");
    }

    public static Sbiactiviteit getSbiactiviteitSample2() {
        return new Sbiactiviteit()
            .id(2L)
            .hoofdniveau("hoofdniveau2")
            .hoofdniveauomschrijving("hoofdniveauomschrijving2")
            .naamactiviteit("naamactiviteit2")
            .sbicode("sbicode2")
            .sbigroep("sbigroep2")
            .sbigroepomschrijving("sbigroepomschrijving2");
    }

    public static Sbiactiviteit getSbiactiviteitRandomSampleGenerator() {
        return new Sbiactiviteit()
            .id(longCount.incrementAndGet())
            .hoofdniveau(UUID.randomUUID().toString())
            .hoofdniveauomschrijving(UUID.randomUUID().toString())
            .naamactiviteit(UUID.randomUUID().toString())
            .sbicode(UUID.randomUUID().toString())
            .sbigroep(UUID.randomUUID().toString())
            .sbigroepomschrijving(UUID.randomUUID().toString());
    }
}
