package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VullingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vulling getVullingSample1() {
        return new Vulling()
            .id(1L)
            .grondsoort("grondsoort1")
            .key("key1")
            .keyspoor("keyspoor1")
            .kleur("kleur1")
            .projectcd("projectcd1")
            .putnummer("putnummer1")
            .spoornummer("spoornummer1")
            .structuur("structuur1")
            .vlaknummer("vlaknummer1")
            .vullingnummer("vullingnummer1");
    }

    public static Vulling getVullingSample2() {
        return new Vulling()
            .id(2L)
            .grondsoort("grondsoort2")
            .key("key2")
            .keyspoor("keyspoor2")
            .kleur("kleur2")
            .projectcd("projectcd2")
            .putnummer("putnummer2")
            .spoornummer("spoornummer2")
            .structuur("structuur2")
            .vlaknummer("vlaknummer2")
            .vullingnummer("vullingnummer2");
    }

    public static Vulling getVullingRandomSampleGenerator() {
        return new Vulling()
            .id(longCount.incrementAndGet())
            .grondsoort(UUID.randomUUID().toString())
            .key(UUID.randomUUID().toString())
            .keyspoor(UUID.randomUUID().toString())
            .kleur(UUID.randomUUID().toString())
            .projectcd(UUID.randomUUID().toString())
            .putnummer(UUID.randomUUID().toString())
            .spoornummer(UUID.randomUUID().toString())
            .structuur(UUID.randomUUID().toString())
            .vlaknummer(UUID.randomUUID().toString())
            .vullingnummer(UUID.randomUUID().toString());
    }
}
