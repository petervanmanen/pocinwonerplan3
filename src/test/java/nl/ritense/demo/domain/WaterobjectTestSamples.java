package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WaterobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Waterobject getWaterobjectSample1() {
        return new Waterobject()
            .id(1L)
            .breedte("breedte1")
            .hoogte("hoogte1")
            .infiltrerendoppervlak("infiltrerendoppervlak1")
            .infiltrerendvermogen("infiltrerendvermogen1")
            .lengte("lengte1")
            .lozingspunt("lozingspunt1")
            .oppervlakte("oppervlakte1")
            .porositeit("porositeit1")
            .streefdiepte("streefdiepte1")
            .type("type1")
            .typeplus("typeplus1")
            .typeplus2("typeplus21")
            .typevaarwater("typevaarwater1")
            .typewaterplant("typewaterplant1")
            .uitstroomniveau("uitstroomniveau1")
            .vaarwegtraject("vaarwegtraject1")
            .vorm("vorm1")
            .waternaam("waternaam1")
            .waterpeil("waterpeil1")
            .waterpeilwinter("waterpeilwinter1")
            .waterpeilzomer("waterpeilzomer1");
    }

    public static Waterobject getWaterobjectSample2() {
        return new Waterobject()
            .id(2L)
            .breedte("breedte2")
            .hoogte("hoogte2")
            .infiltrerendoppervlak("infiltrerendoppervlak2")
            .infiltrerendvermogen("infiltrerendvermogen2")
            .lengte("lengte2")
            .lozingspunt("lozingspunt2")
            .oppervlakte("oppervlakte2")
            .porositeit("porositeit2")
            .streefdiepte("streefdiepte2")
            .type("type2")
            .typeplus("typeplus2")
            .typeplus2("typeplus22")
            .typevaarwater("typevaarwater2")
            .typewaterplant("typewaterplant2")
            .uitstroomniveau("uitstroomniveau2")
            .vaarwegtraject("vaarwegtraject2")
            .vorm("vorm2")
            .waternaam("waternaam2")
            .waterpeil("waterpeil2")
            .waterpeilwinter("waterpeilwinter2")
            .waterpeilzomer("waterpeilzomer2");
    }

    public static Waterobject getWaterobjectRandomSampleGenerator() {
        return new Waterobject()
            .id(longCount.incrementAndGet())
            .breedte(UUID.randomUUID().toString())
            .hoogte(UUID.randomUUID().toString())
            .infiltrerendoppervlak(UUID.randomUUID().toString())
            .infiltrerendvermogen(UUID.randomUUID().toString())
            .lengte(UUID.randomUUID().toString())
            .lozingspunt(UUID.randomUUID().toString())
            .oppervlakte(UUID.randomUUID().toString())
            .porositeit(UUID.randomUUID().toString())
            .streefdiepte(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .typeplus(UUID.randomUUID().toString())
            .typeplus2(UUID.randomUUID().toString())
            .typevaarwater(UUID.randomUUID().toString())
            .typewaterplant(UUID.randomUUID().toString())
            .uitstroomniveau(UUID.randomUUID().toString())
            .vaarwegtraject(UUID.randomUUID().toString())
            .vorm(UUID.randomUUID().toString())
            .waternaam(UUID.randomUUID().toString())
            .waterpeil(UUID.randomUUID().toString())
            .waterpeilwinter(UUID.randomUUID().toString())
            .waterpeilzomer(UUID.randomUUID().toString());
    }
}
