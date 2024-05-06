package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MulderfeitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Mulderfeit getMulderfeitSample1() {
        return new Mulderfeit()
            .id(1L)
            .bonnummer("bonnummer1")
            .dienstcd("dienstcd1")
            .organisatie("organisatie1")
            .overtreding("overtreding1")
            .redenseponeren("redenseponeren1")
            .vorderingnummer("vorderingnummer1");
    }

    public static Mulderfeit getMulderfeitSample2() {
        return new Mulderfeit()
            .id(2L)
            .bonnummer("bonnummer2")
            .dienstcd("dienstcd2")
            .organisatie("organisatie2")
            .overtreding("overtreding2")
            .redenseponeren("redenseponeren2")
            .vorderingnummer("vorderingnummer2");
    }

    public static Mulderfeit getMulderfeitRandomSampleGenerator() {
        return new Mulderfeit()
            .id(longCount.incrementAndGet())
            .bonnummer(UUID.randomUUID().toString())
            .dienstcd(UUID.randomUUID().toString())
            .organisatie(UUID.randomUUID().toString())
            .overtreding(UUID.randomUUID().toString())
            .redenseponeren(UUID.randomUUID().toString())
            .vorderingnummer(UUID.randomUUID().toString());
    }
}
