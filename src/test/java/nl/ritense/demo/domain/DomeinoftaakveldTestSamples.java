package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class DomeinoftaakveldTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Domeinoftaakveld getDomeinoftaakveldSample1() {
        return new Domeinoftaakveld().id(1L);
    }

    public static Domeinoftaakveld getDomeinoftaakveldSample2() {
        return new Domeinoftaakveld().id(2L);
    }

    public static Domeinoftaakveld getDomeinoftaakveldRandomSampleGenerator() {
        return new Domeinoftaakveld().id(longCount.incrementAndGet());
    }
}
