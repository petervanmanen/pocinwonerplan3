package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProvincieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Provincie getProvincieSample1() {
        return new Provincie()
            .id(1L)
            .hoofdstad("hoofdstad1")
            .oppervlakte("oppervlakte1")
            .oppervlakteland("oppervlakteland1")
            .provinciecode("provinciecode1")
            .provincienaam("provincienaam1");
    }

    public static Provincie getProvincieSample2() {
        return new Provincie()
            .id(2L)
            .hoofdstad("hoofdstad2")
            .oppervlakte("oppervlakte2")
            .oppervlakteland("oppervlakteland2")
            .provinciecode("provinciecode2")
            .provincienaam("provincienaam2");
    }

    public static Provincie getProvincieRandomSampleGenerator() {
        return new Provincie()
            .id(longCount.incrementAndGet())
            .hoofdstad(UUID.randomUUID().toString())
            .oppervlakte(UUID.randomUUID().toString())
            .oppervlakteland(UUID.randomUUID().toString())
            .provinciecode(UUID.randomUUID().toString())
            .provincienaam(UUID.randomUUID().toString());
    }
}
