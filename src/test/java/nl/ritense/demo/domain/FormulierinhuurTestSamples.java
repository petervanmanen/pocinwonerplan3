package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FormulierinhuurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Formulierinhuur getFormulierinhuurSample1() {
        return new Formulierinhuur()
            .id(1L)
            .akkoordfinancieeladviseur("akkoordfinancieeladviseur1")
            .akkoordhradviseur("akkoordhradviseur1")
            .functienaaminhuur("functienaaminhuur1");
    }

    public static Formulierinhuur getFormulierinhuurSample2() {
        return new Formulierinhuur()
            .id(2L)
            .akkoordfinancieeladviseur("akkoordfinancieeladviseur2")
            .akkoordhradviseur("akkoordhradviseur2")
            .functienaaminhuur("functienaaminhuur2");
    }

    public static Formulierinhuur getFormulierinhuurRandomSampleGenerator() {
        return new Formulierinhuur()
            .id(longCount.incrementAndGet())
            .akkoordfinancieeladviseur(UUID.randomUUID().toString())
            .akkoordhradviseur(UUID.randomUUID().toString())
            .functienaaminhuur(UUID.randomUUID().toString());
    }
}
