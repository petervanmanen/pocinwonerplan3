package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FunctioneelgebiedTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Functioneelgebied getFunctioneelgebiedSample1() {
        return new Functioneelgebied()
            .id(1L)
            .functioneelgebiedcode("functioneelgebiedcode1")
            .functioneelgebiednaam("functioneelgebiednaam1")
            .omtrek("omtrek1")
            .oppervlakte("oppervlakte1");
    }

    public static Functioneelgebied getFunctioneelgebiedSample2() {
        return new Functioneelgebied()
            .id(2L)
            .functioneelgebiedcode("functioneelgebiedcode2")
            .functioneelgebiednaam("functioneelgebiednaam2")
            .omtrek("omtrek2")
            .oppervlakte("oppervlakte2");
    }

    public static Functioneelgebied getFunctioneelgebiedRandomSampleGenerator() {
        return new Functioneelgebied()
            .id(longCount.incrementAndGet())
            .functioneelgebiedcode(UUID.randomUUID().toString())
            .functioneelgebiednaam(UUID.randomUUID().toString())
            .omtrek(UUID.randomUUID().toString())
            .oppervlakte(UUID.randomUUID().toString());
    }
}
