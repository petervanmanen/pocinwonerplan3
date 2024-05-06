package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProcesverbaalonderwijsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Procesverbaalonderwijs getProcesverbaalonderwijsSample1() {
        return new Procesverbaalonderwijs()
            .id(1L)
            .geldboetevoorwaardelijk("geldboetevoorwaardelijk1")
            .opmerkingen("opmerkingen1")
            .proeftijd("proeftijd1")
            .reden("reden1")
            .sanctiesoort("sanctiesoort1")
            .uitspraak("uitspraak1")
            .verzuimsoort("verzuimsoort1");
    }

    public static Procesverbaalonderwijs getProcesverbaalonderwijsSample2() {
        return new Procesverbaalonderwijs()
            .id(2L)
            .geldboetevoorwaardelijk("geldboetevoorwaardelijk2")
            .opmerkingen("opmerkingen2")
            .proeftijd("proeftijd2")
            .reden("reden2")
            .sanctiesoort("sanctiesoort2")
            .uitspraak("uitspraak2")
            .verzuimsoort("verzuimsoort2");
    }

    public static Procesverbaalonderwijs getProcesverbaalonderwijsRandomSampleGenerator() {
        return new Procesverbaalonderwijs()
            .id(longCount.incrementAndGet())
            .geldboetevoorwaardelijk(UUID.randomUUID().toString())
            .opmerkingen(UUID.randomUUID().toString())
            .proeftijd(UUID.randomUUID().toString())
            .reden(UUID.randomUUID().toString())
            .sanctiesoort(UUID.randomUUID().toString())
            .uitspraak(UUID.randomUUID().toString())
            .verzuimsoort(UUID.randomUUID().toString());
    }
}
