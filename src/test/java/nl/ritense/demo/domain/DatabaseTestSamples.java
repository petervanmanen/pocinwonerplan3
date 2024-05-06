package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DatabaseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Database getDatabaseSample1() {
        return new Database()
            .id(1L)
            .architectuur("architectuur1")
            .database("database1")
            .databaseversie("databaseversie1")
            .dbms("dbms1")
            .omschrijving("omschrijving1")
            .otap("otap1")
            .vlan("vlan1");
    }

    public static Database getDatabaseSample2() {
        return new Database()
            .id(2L)
            .architectuur("architectuur2")
            .database("database2")
            .databaseversie("databaseversie2")
            .dbms("dbms2")
            .omschrijving("omschrijving2")
            .otap("otap2")
            .vlan("vlan2");
    }

    public static Database getDatabaseRandomSampleGenerator() {
        return new Database()
            .id(longCount.incrementAndGet())
            .architectuur(UUID.randomUUID().toString())
            .database(UUID.randomUUID().toString())
            .databaseversie(UUID.randomUUID().toString())
            .dbms(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .otap(UUID.randomUUID().toString())
            .vlan(UUID.randomUUID().toString());
    }
}
