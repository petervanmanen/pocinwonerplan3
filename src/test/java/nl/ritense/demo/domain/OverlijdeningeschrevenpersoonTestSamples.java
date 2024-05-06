package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OverlijdeningeschrevenpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Overlijdeningeschrevenpersoon getOverlijdeningeschrevenpersoonSample1() {
        return new Overlijdeningeschrevenpersoon()
            .id(1L)
            .datumoverlijden("datumoverlijden1")
            .landoverlijden("landoverlijden1")
            .overlijdensplaats("overlijdensplaats1");
    }

    public static Overlijdeningeschrevenpersoon getOverlijdeningeschrevenpersoonSample2() {
        return new Overlijdeningeschrevenpersoon()
            .id(2L)
            .datumoverlijden("datumoverlijden2")
            .landoverlijden("landoverlijden2")
            .overlijdensplaats("overlijdensplaats2");
    }

    public static Overlijdeningeschrevenpersoon getOverlijdeningeschrevenpersoonRandomSampleGenerator() {
        return new Overlijdeningeschrevenpersoon()
            .id(longCount.incrementAndGet())
            .datumoverlijden(UUID.randomUUID().toString())
            .landoverlijden(UUID.randomUUID().toString())
            .overlijdensplaats(UUID.randomUUID().toString());
    }
}
