package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ReisdocumentsoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Reisdocumentsoort getReisdocumentsoortSample1() {
        return new Reisdocumentsoort().id(1L).reisdocumentcode("reisdocumentcode1").reisdocumentomschrijving("reisdocumentomschrijving1");
    }

    public static Reisdocumentsoort getReisdocumentsoortSample2() {
        return new Reisdocumentsoort().id(2L).reisdocumentcode("reisdocumentcode2").reisdocumentomschrijving("reisdocumentomschrijving2");
    }

    public static Reisdocumentsoort getReisdocumentsoortRandomSampleGenerator() {
        return new Reisdocumentsoort()
            .id(longCount.incrementAndGet())
            .reisdocumentcode(UUID.randomUUID().toString())
            .reisdocumentomschrijving(UUID.randomUUID().toString());
    }
}
