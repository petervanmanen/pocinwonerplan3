package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerkeersdrempelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verkeersdrempel getVerkeersdrempelSample1() {
        return new Verkeersdrempel().id(1L).ontwerpsnelheid("ontwerpsnelheid1").type("type1").typeplus("typeplus1");
    }

    public static Verkeersdrempel getVerkeersdrempelSample2() {
        return new Verkeersdrempel().id(2L).ontwerpsnelheid("ontwerpsnelheid2").type("type2").typeplus("typeplus2");
    }

    public static Verkeersdrempel getVerkeersdrempelRandomSampleGenerator() {
        return new Verkeersdrempel()
            .id(longCount.incrementAndGet())
            .ontwerpsnelheid(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .typeplus(UUID.randomUUID().toString());
    }
}
