package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerblijfsobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verblijfsobject getVerblijfsobjectSample1() {
        return new Verblijfsobject()
            .id(1L)
            .aantalkamers("aantalkamers1")
            .documentnr("documentnr1")
            .gebruiksdoel("gebruiksdoel1")
            .geometrie("geometrie1")
            .hoogstebouwlaagverblijfsobject("hoogstebouwlaagverblijfsobject1")
            .identificatie("identificatie1")
            .laagstebouwlaagverblijfsobject("laagstebouwlaagverblijfsobject1")
            .ontsluitingverdieping("ontsluitingverdieping1")
            .soortwoonobject("soortwoonobject1")
            .status("status1")
            .toegangbouwlaagverblijfsobject("toegangbouwlaagverblijfsobject1")
            .versie("versie1");
    }

    public static Verblijfsobject getVerblijfsobjectSample2() {
        return new Verblijfsobject()
            .id(2L)
            .aantalkamers("aantalkamers2")
            .documentnr("documentnr2")
            .gebruiksdoel("gebruiksdoel2")
            .geometrie("geometrie2")
            .hoogstebouwlaagverblijfsobject("hoogstebouwlaagverblijfsobject2")
            .identificatie("identificatie2")
            .laagstebouwlaagverblijfsobject("laagstebouwlaagverblijfsobject2")
            .ontsluitingverdieping("ontsluitingverdieping2")
            .soortwoonobject("soortwoonobject2")
            .status("status2")
            .toegangbouwlaagverblijfsobject("toegangbouwlaagverblijfsobject2")
            .versie("versie2");
    }

    public static Verblijfsobject getVerblijfsobjectRandomSampleGenerator() {
        return new Verblijfsobject()
            .id(longCount.incrementAndGet())
            .aantalkamers(UUID.randomUUID().toString())
            .documentnr(UUID.randomUUID().toString())
            .gebruiksdoel(UUID.randomUUID().toString())
            .geometrie(UUID.randomUUID().toString())
            .hoogstebouwlaagverblijfsobject(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .laagstebouwlaagverblijfsobject(UUID.randomUUID().toString())
            .ontsluitingverdieping(UUID.randomUUID().toString())
            .soortwoonobject(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .toegangbouwlaagverblijfsobject(UUID.randomUUID().toString())
            .versie(UUID.randomUUID().toString());
    }
}
