package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Lstclass1TestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Lstclass1 getLstclass1Sample1() {
        return new Lstclass1()
            .id(1L)
            .waarde(1)
            .dwhrecordid(1)
            .dwhodsrecordid(1)
            .dwhrunid(1)
            .dwhbron("dwhbron1")
            .dwhactueel(1)
            .lstclass1id(1);
    }

    public static Lstclass1 getLstclass1Sample2() {
        return new Lstclass1()
            .id(2L)
            .waarde(2)
            .dwhrecordid(2)
            .dwhodsrecordid(2)
            .dwhrunid(2)
            .dwhbron("dwhbron2")
            .dwhactueel(2)
            .lstclass1id(2);
    }

    public static Lstclass1 getLstclass1RandomSampleGenerator() {
        return new Lstclass1()
            .id(longCount.incrementAndGet())
            .waarde(intCount.incrementAndGet())
            .dwhrecordid(intCount.incrementAndGet())
            .dwhodsrecordid(intCount.incrementAndGet())
            .dwhrunid(intCount.incrementAndGet())
            .dwhbron(UUID.randomUUID().toString())
            .dwhactueel(intCount.incrementAndGet())
            .lstclass1id(intCount.incrementAndGet());
    }
}
