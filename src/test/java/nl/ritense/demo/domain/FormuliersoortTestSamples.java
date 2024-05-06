package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FormuliersoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Formuliersoort getFormuliersoortSample1() {
        return new Formuliersoort().id(1L).ingebruik("ingebruik1").naam("naam1").onderwerp("onderwerp1");
    }

    public static Formuliersoort getFormuliersoortSample2() {
        return new Formuliersoort().id(2L).ingebruik("ingebruik2").naam("naam2").onderwerp("onderwerp2");
    }

    public static Formuliersoort getFormuliersoortRandomSampleGenerator() {
        return new Formuliersoort()
            .id(longCount.incrementAndGet())
            .ingebruik(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .onderwerp(UUID.randomUUID().toString());
    }
}
