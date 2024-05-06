package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InstructieregelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Instructieregel getInstructieregelSample1() {
        return new Instructieregel()
            .id(1L)
            .instructieregelinstrument("instructieregelinstrument1")
            .instructieregeltaakuitoefening("instructieregeltaakuitoefening1");
    }

    public static Instructieregel getInstructieregelSample2() {
        return new Instructieregel()
            .id(2L)
            .instructieregelinstrument("instructieregelinstrument2")
            .instructieregeltaakuitoefening("instructieregeltaakuitoefening2");
    }

    public static Instructieregel getInstructieregelRandomSampleGenerator() {
        return new Instructieregel()
            .id(longCount.incrementAndGet())
            .instructieregelinstrument(UUID.randomUUID().toString())
            .instructieregeltaakuitoefening(UUID.randomUUID().toString());
    }
}
