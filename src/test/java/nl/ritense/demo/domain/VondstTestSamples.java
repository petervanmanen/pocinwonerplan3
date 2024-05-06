package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VondstTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vondst getVondstSample1() {
        return new Vondst()
            .id(1L)
            .key("key1")
            .keyvulling("keyvulling1")
            .omschrijving("omschrijving1")
            .omstandigheden("omstandigheden1")
            .projectcd("projectcd1")
            .putnummer("putnummer1")
            .spoornummer("spoornummer1")
            .vlaknummer("vlaknummer1")
            .vondstnummer("vondstnummer1")
            .vullingnummer("vullingnummer1")
            .xcoordinaat("xcoordinaat1")
            .ycoordinaat("ycoordinaat1");
    }

    public static Vondst getVondstSample2() {
        return new Vondst()
            .id(2L)
            .key("key2")
            .keyvulling("keyvulling2")
            .omschrijving("omschrijving2")
            .omstandigheden("omstandigheden2")
            .projectcd("projectcd2")
            .putnummer("putnummer2")
            .spoornummer("spoornummer2")
            .vlaknummer("vlaknummer2")
            .vondstnummer("vondstnummer2")
            .vullingnummer("vullingnummer2")
            .xcoordinaat("xcoordinaat2")
            .ycoordinaat("ycoordinaat2");
    }

    public static Vondst getVondstRandomSampleGenerator() {
        return new Vondst()
            .id(longCount.incrementAndGet())
            .key(UUID.randomUUID().toString())
            .keyvulling(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .omstandigheden(UUID.randomUUID().toString())
            .projectcd(UUID.randomUUID().toString())
            .putnummer(UUID.randomUUID().toString())
            .spoornummer(UUID.randomUUID().toString())
            .vlaknummer(UUID.randomUUID().toString())
            .vondstnummer(UUID.randomUUID().toString())
            .vullingnummer(UUID.randomUUID().toString())
            .xcoordinaat(UUID.randomUUID().toString())
            .ycoordinaat(UUID.randomUUID().toString());
    }
}
