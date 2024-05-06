package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ServerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Server getServerSample1() {
        return new Server()
            .id(1L)
            .ipadres("ipadres1")
            .locatie("locatie1")
            .organisatie("organisatie1")
            .serienummer("serienummer1")
            .serverid("serverid1")
            .servertype("servertype1")
            .vlan("vlan1");
    }

    public static Server getServerSample2() {
        return new Server()
            .id(2L)
            .ipadres("ipadres2")
            .locatie("locatie2")
            .organisatie("organisatie2")
            .serienummer("serienummer2")
            .serverid("serverid2")
            .servertype("servertype2")
            .vlan("vlan2");
    }

    public static Server getServerRandomSampleGenerator() {
        return new Server()
            .id(longCount.incrementAndGet())
            .ipadres(UUID.randomUUID().toString())
            .locatie(UUID.randomUUID().toString())
            .organisatie(UUID.randomUUID().toString())
            .serienummer(UUID.randomUUID().toString())
            .serverid(UUID.randomUUID().toString())
            .servertype(UUID.randomUUID().toString())
            .vlan(UUID.randomUUID().toString());
    }
}
