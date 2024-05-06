package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BouwstijlTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bouwstijl getBouwstijlSample1() {
        return new Bouwstijl().id(1L).hoofdstijl("hoofdstijl1").substijl("substijl1").toelichting("toelichting1").zuiverheid("zuiverheid1");
    }

    public static Bouwstijl getBouwstijlSample2() {
        return new Bouwstijl().id(2L).hoofdstijl("hoofdstijl2").substijl("substijl2").toelichting("toelichting2").zuiverheid("zuiverheid2");
    }

    public static Bouwstijl getBouwstijlRandomSampleGenerator() {
        return new Bouwstijl()
            .id(longCount.incrementAndGet())
            .hoofdstijl(UUID.randomUUID().toString())
            .substijl(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString())
            .zuiverheid(UUID.randomUUID().toString());
    }
}
