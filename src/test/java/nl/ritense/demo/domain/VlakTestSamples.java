package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VlakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vlak getVlakSample1() {
        return new Vlak()
            .id(1L)
            .dieptetot("dieptetot1")
            .dieptevan("dieptevan1")
            .key("key1")
            .keyput("keyput1")
            .projectcd("projectcd1")
            .putnummer("putnummer1")
            .vlaknummer("vlaknummer1");
    }

    public static Vlak getVlakSample2() {
        return new Vlak()
            .id(2L)
            .dieptetot("dieptetot2")
            .dieptevan("dieptevan2")
            .key("key2")
            .keyput("keyput2")
            .projectcd("projectcd2")
            .putnummer("putnummer2")
            .vlaknummer("vlaknummer2");
    }

    public static Vlak getVlakRandomSampleGenerator() {
        return new Vlak()
            .id(longCount.incrementAndGet())
            .dieptetot(UUID.randomUUID().toString())
            .dieptevan(UUID.randomUUID().toString())
            .key(UUID.randomUUID().toString())
            .keyput(UUID.randomUUID().toString())
            .projectcd(UUID.randomUUID().toString())
            .putnummer(UUID.randomUUID().toString())
            .vlaknummer(UUID.randomUUID().toString());
    }
}
