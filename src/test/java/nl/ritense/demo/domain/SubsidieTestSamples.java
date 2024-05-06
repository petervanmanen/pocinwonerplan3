package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SubsidieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Subsidie getSubsidieSample1() {
        return new Subsidie()
            .id(1L)
            .accountantscontrole("accountantscontrole1")
            .doelstelling("doelstelling1")
            .niveau("niveau1")
            .onderwerp("onderwerp1")
            .opmerkingen("opmerkingen1")
            .opmerkingenvoorschotten("opmerkingenvoorschotten1")
            .prestatiesubsidie("prestatiesubsidie1")
            .socialreturnnagekomen("socialreturnnagekomen1")
            .socialreturnverplichting("socialreturnverplichting1")
            .status("status1")
            .subsidiesoort("subsidiesoort1")
            .uitgaandesubsidie("uitgaandesubsidie1");
    }

    public static Subsidie getSubsidieSample2() {
        return new Subsidie()
            .id(2L)
            .accountantscontrole("accountantscontrole2")
            .doelstelling("doelstelling2")
            .niveau("niveau2")
            .onderwerp("onderwerp2")
            .opmerkingen("opmerkingen2")
            .opmerkingenvoorschotten("opmerkingenvoorschotten2")
            .prestatiesubsidie("prestatiesubsidie2")
            .socialreturnnagekomen("socialreturnnagekomen2")
            .socialreturnverplichting("socialreturnverplichting2")
            .status("status2")
            .subsidiesoort("subsidiesoort2")
            .uitgaandesubsidie("uitgaandesubsidie2");
    }

    public static Subsidie getSubsidieRandomSampleGenerator() {
        return new Subsidie()
            .id(longCount.incrementAndGet())
            .accountantscontrole(UUID.randomUUID().toString())
            .doelstelling(UUID.randomUUID().toString())
            .niveau(UUID.randomUUID().toString())
            .onderwerp(UUID.randomUUID().toString())
            .opmerkingen(UUID.randomUUID().toString())
            .opmerkingenvoorschotten(UUID.randomUUID().toString())
            .prestatiesubsidie(UUID.randomUUID().toString())
            .socialreturnnagekomen(UUID.randomUUID().toString())
            .socialreturnverplichting(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .subsidiesoort(UUID.randomUUID().toString())
            .uitgaandesubsidie(UUID.randomUUID().toString());
    }
}
