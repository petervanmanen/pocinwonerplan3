package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MaterielehistorieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Materielehistorie getMaterielehistorieSample1() {
        return new Materielehistorie()
            .id(1L)
            .datumbegingeldigheidgegevens("datumbegingeldigheidgegevens1")
            .datumeindegeldigheidgegevens("datumeindegeldigheidgegevens1");
    }

    public static Materielehistorie getMaterielehistorieSample2() {
        return new Materielehistorie()
            .id(2L)
            .datumbegingeldigheidgegevens("datumbegingeldigheidgegevens2")
            .datumeindegeldigheidgegevens("datumeindegeldigheidgegevens2");
    }

    public static Materielehistorie getMaterielehistorieRandomSampleGenerator() {
        return new Materielehistorie()
            .id(longCount.incrementAndGet())
            .datumbegingeldigheidgegevens(UUID.randomUUID().toString())
            .datumeindegeldigheidgegevens(UUID.randomUUID().toString());
    }
}
