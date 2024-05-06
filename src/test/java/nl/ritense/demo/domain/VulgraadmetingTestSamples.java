package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VulgraadmetingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vulgraadmeting getVulgraadmetingSample1() {
        return new Vulgraadmeting().id(1L).tijdstip("tijdstip1").vulgraad("vulgraad1").vullinggewicht("vullinggewicht1");
    }

    public static Vulgraadmeting getVulgraadmetingSample2() {
        return new Vulgraadmeting().id(2L).tijdstip("tijdstip2").vulgraad("vulgraad2").vullinggewicht("vullinggewicht2");
    }

    public static Vulgraadmeting getVulgraadmetingRandomSampleGenerator() {
        return new Vulgraadmeting()
            .id(longCount.incrementAndGet())
            .tijdstip(UUID.randomUUID().toString())
            .vulgraad(UUID.randomUUID().toString())
            .vullinggewicht(UUID.randomUUID().toString());
    }
}
