package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PandTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Pand getPandSample1() {
        return new Pand()
            .id(1L)
            .brutoinhoudpand("brutoinhoudpand1")
            .geometriebovenaanzicht("geometriebovenaanzicht1")
            .geometriemaaiveld("geometriemaaiveld1")
            .geometriepunt("geometriepunt1")
            .hoogstebouwlaagpand("hoogstebouwlaagpand1")
            .identificatie("identificatie1")
            .laagstebouwlaagpand("laagstebouwlaagpand1")
            .oorspronkelijkbouwjaar("oorspronkelijkbouwjaar1")
            .oppervlakte("oppervlakte1")
            .relatievehoogteliggingpand("relatievehoogteliggingpand1")
            .status("status1")
            .statusvoortgangbouw("statusvoortgangbouw1")
            .versie("versie1");
    }

    public static Pand getPandSample2() {
        return new Pand()
            .id(2L)
            .brutoinhoudpand("brutoinhoudpand2")
            .geometriebovenaanzicht("geometriebovenaanzicht2")
            .geometriemaaiveld("geometriemaaiveld2")
            .geometriepunt("geometriepunt2")
            .hoogstebouwlaagpand("hoogstebouwlaagpand2")
            .identificatie("identificatie2")
            .laagstebouwlaagpand("laagstebouwlaagpand2")
            .oorspronkelijkbouwjaar("oorspronkelijkbouwjaar2")
            .oppervlakte("oppervlakte2")
            .relatievehoogteliggingpand("relatievehoogteliggingpand2")
            .status("status2")
            .statusvoortgangbouw("statusvoortgangbouw2")
            .versie("versie2");
    }

    public static Pand getPandRandomSampleGenerator() {
        return new Pand()
            .id(longCount.incrementAndGet())
            .brutoinhoudpand(UUID.randomUUID().toString())
            .geometriebovenaanzicht(UUID.randomUUID().toString())
            .geometriemaaiveld(UUID.randomUUID().toString())
            .geometriepunt(UUID.randomUUID().toString())
            .hoogstebouwlaagpand(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .laagstebouwlaagpand(UUID.randomUUID().toString())
            .oorspronkelijkbouwjaar(UUID.randomUUID().toString())
            .oppervlakte(UUID.randomUUID().toString())
            .relatievehoogteliggingpand(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .statusvoortgangbouw(UUID.randomUUID().toString())
            .versie(UUID.randomUUID().toString());
    }
}
