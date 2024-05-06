package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KoopsomkadastraleonroerendezaakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Koopsomkadastraleonroerendezaak getKoopsomkadastraleonroerendezaakSample1() {
        return new Koopsomkadastraleonroerendezaak().id(1L).datumtransactie("datumtransactie1").koopsom("koopsom1");
    }

    public static Koopsomkadastraleonroerendezaak getKoopsomkadastraleonroerendezaakSample2() {
        return new Koopsomkadastraleonroerendezaak().id(2L).datumtransactie("datumtransactie2").koopsom("koopsom2");
    }

    public static Koopsomkadastraleonroerendezaak getKoopsomkadastraleonroerendezaakRandomSampleGenerator() {
        return new Koopsomkadastraleonroerendezaak()
            .id(longCount.incrementAndGet())
            .datumtransactie(UUID.randomUUID().toString())
            .koopsom(UUID.randomUUID().toString());
    }
}
