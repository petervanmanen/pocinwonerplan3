package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerzuimmeldingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verzuimmelding getVerzuimmeldingSample1() {
        return new Verzuimmelding().id(1L).voorstelschool("voorstelschool1");
    }

    public static Verzuimmelding getVerzuimmeldingSample2() {
        return new Verzuimmelding().id(2L).voorstelschool("voorstelschool2");
    }

    public static Verzuimmelding getVerzuimmeldingRandomSampleGenerator() {
        return new Verzuimmelding().id(longCount.incrementAndGet()).voorstelschool(UUID.randomUUID().toString());
    }
}
