package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SpoorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Spoor getSpoorSample1() {
        return new Spoor()
            .id(1L)
            .aard("aard1")
            .beschrijving("beschrijving1")
            .datering("datering1")
            .hoogteboven("hoogteboven1")
            .hoogteonder("hoogteonder1")
            .key("key1")
            .keyvlak("keyvlak1")
            .projectcd("projectcd1")
            .putnummer("putnummer1")
            .spoornummer("spoornummer1")
            .vlaknummer("vlaknummer1")
            .vorm("vorm1");
    }

    public static Spoor getSpoorSample2() {
        return new Spoor()
            .id(2L)
            .aard("aard2")
            .beschrijving("beschrijving2")
            .datering("datering2")
            .hoogteboven("hoogteboven2")
            .hoogteonder("hoogteonder2")
            .key("key2")
            .keyvlak("keyvlak2")
            .projectcd("projectcd2")
            .putnummer("putnummer2")
            .spoornummer("spoornummer2")
            .vlaknummer("vlaknummer2")
            .vorm("vorm2");
    }

    public static Spoor getSpoorRandomSampleGenerator() {
        return new Spoor()
            .id(longCount.incrementAndGet())
            .aard(UUID.randomUUID().toString())
            .beschrijving(UUID.randomUUID().toString())
            .datering(UUID.randomUUID().toString())
            .hoogteboven(UUID.randomUUID().toString())
            .hoogteonder(UUID.randomUUID().toString())
            .key(UUID.randomUUID().toString())
            .keyvlak(UUID.randomUUID().toString())
            .projectcd(UUID.randomUUID().toString())
            .putnummer(UUID.randomUUID().toString())
            .spoornummer(UUID.randomUUID().toString())
            .vlaknummer(UUID.randomUUID().toString())
            .vorm(UUID.randomUUID().toString());
    }
}
