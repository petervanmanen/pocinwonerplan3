package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KostenplaatsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kostenplaats getKostenplaatsSample1() {
        return new Kostenplaats()
            .id(1L)
            .btwcode("btwcode1")
            .btwomschrijving("btwomschrijving1")
            .kostenplaatssoortcode("kostenplaatssoortcode1")
            .kostenplaatssoortomschrijving("kostenplaatssoortomschrijving1")
            .kostenplaatstypecode("kostenplaatstypecode1")
            .kostenplaatstypeomschrijving("kostenplaatstypeomschrijving1")
            .naam("naam1")
            .omschrijving("omschrijving1");
    }

    public static Kostenplaats getKostenplaatsSample2() {
        return new Kostenplaats()
            .id(2L)
            .btwcode("btwcode2")
            .btwomschrijving("btwomschrijving2")
            .kostenplaatssoortcode("kostenplaatssoortcode2")
            .kostenplaatssoortomschrijving("kostenplaatssoortomschrijving2")
            .kostenplaatstypecode("kostenplaatstypecode2")
            .kostenplaatstypeomschrijving("kostenplaatstypeomschrijving2")
            .naam("naam2")
            .omschrijving("omschrijving2");
    }

    public static Kostenplaats getKostenplaatsRandomSampleGenerator() {
        return new Kostenplaats()
            .id(longCount.incrementAndGet())
            .btwcode(UUID.randomUUID().toString())
            .btwomschrijving(UUID.randomUUID().toString())
            .kostenplaatssoortcode(UUID.randomUUID().toString())
            .kostenplaatssoortomschrijving(UUID.randomUUID().toString())
            .kostenplaatstypecode(UUID.randomUUID().toString())
            .kostenplaatstypeomschrijving(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
