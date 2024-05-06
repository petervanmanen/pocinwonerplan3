package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NaheffingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Naheffing getNaheffingSample1() {
        return new Naheffing()
            .id(1L)
            .bonnummer("bonnummer1")
            .dienstcd("dienstcd1")
            .organisatie("organisatie1")
            .overtreding("overtreding1")
            .redenseponeren("redenseponeren1")
            .vorderingnummer("vorderingnummer1");
    }

    public static Naheffing getNaheffingSample2() {
        return new Naheffing()
            .id(2L)
            .bonnummer("bonnummer2")
            .dienstcd("dienstcd2")
            .organisatie("organisatie2")
            .overtreding("overtreding2")
            .redenseponeren("redenseponeren2")
            .vorderingnummer("vorderingnummer2");
    }

    public static Naheffing getNaheffingRandomSampleGenerator() {
        return new Naheffing()
            .id(longCount.incrementAndGet())
            .bonnummer(UUID.randomUUID().toString())
            .dienstcd(UUID.randomUUID().toString())
            .organisatie(UUID.randomUUID().toString())
            .overtreding(UUID.randomUUID().toString())
            .redenseponeren(UUID.randomUUID().toString())
            .vorderingnummer(UUID.randomUUID().toString());
    }
}
