package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KadastralegemeenteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kadastralegemeente getKadastralegemeenteSample1() {
        return new Kadastralegemeente().id(1L).kadastralegemeentecode("kadastralegemeentecode1").naam("naam1");
    }

    public static Kadastralegemeente getKadastralegemeenteSample2() {
        return new Kadastralegemeente().id(2L).kadastralegemeentecode("kadastralegemeentecode2").naam("naam2");
    }

    public static Kadastralegemeente getKadastralegemeenteRandomSampleGenerator() {
        return new Kadastralegemeente()
            .id(longCount.incrementAndGet())
            .kadastralegemeentecode(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString());
    }
}
