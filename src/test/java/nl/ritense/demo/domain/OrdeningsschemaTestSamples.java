package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OrdeningsschemaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ordeningsschema getOrdeningsschemaSample1() {
        return new Ordeningsschema().id(1L).naam("naam1").text("text1");
    }

    public static Ordeningsschema getOrdeningsschemaSample2() {
        return new Ordeningsschema().id(2L).naam("naam2").text("text2");
    }

    public static Ordeningsschema getOrdeningsschemaRandomSampleGenerator() {
        return new Ordeningsschema().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString()).text(UUID.randomUUID().toString());
    }
}
