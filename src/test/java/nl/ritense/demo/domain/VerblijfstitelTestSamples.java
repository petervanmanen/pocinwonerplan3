package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerblijfstitelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verblijfstitel getVerblijfstitelSample1() {
        return new Verblijfstitel().id(1L).aanduidingverblijfstitel("aanduidingverblijfstitel1").verblijfstitelcode("verblijfstitelcode1");
    }

    public static Verblijfstitel getVerblijfstitelSample2() {
        return new Verblijfstitel().id(2L).aanduidingverblijfstitel("aanduidingverblijfstitel2").verblijfstitelcode("verblijfstitelcode2");
    }

    public static Verblijfstitel getVerblijfstitelRandomSampleGenerator() {
        return new Verblijfstitel()
            .id(longCount.incrementAndGet())
            .aanduidingverblijfstitel(UUID.randomUUID().toString())
            .verblijfstitelcode(UUID.randomUUID().toString());
    }
}
