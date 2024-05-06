package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VuilniswagenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vuilniswagen getVuilniswagenSample1() {
        return new Vuilniswagen().id(1L).code("code1").kenteken("kenteken1").type("type1");
    }

    public static Vuilniswagen getVuilniswagenSample2() {
        return new Vuilniswagen().id(2L).code("code2").kenteken("kenteken2").type("type2");
    }

    public static Vuilniswagen getVuilniswagenRandomSampleGenerator() {
        return new Vuilniswagen()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .kenteken(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
