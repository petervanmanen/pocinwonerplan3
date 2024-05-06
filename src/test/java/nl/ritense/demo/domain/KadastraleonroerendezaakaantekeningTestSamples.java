package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KadastraleonroerendezaakaantekeningTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kadastraleonroerendezaakaantekening getKadastraleonroerendezaakaantekeningSample1() {
        return new Kadastraleonroerendezaakaantekening()
            .id(1L)
            .aardaantekeningkadastraalobject("aardaantekeningkadastraalobject1")
            .beschrijvingaantekeningkadastraalobject("beschrijvingaantekeningkadastraalobject1")
            .kadasteridentificatieaantekening("kadasteridentificatieaantekening1");
    }

    public static Kadastraleonroerendezaakaantekening getKadastraleonroerendezaakaantekeningSample2() {
        return new Kadastraleonroerendezaakaantekening()
            .id(2L)
            .aardaantekeningkadastraalobject("aardaantekeningkadastraalobject2")
            .beschrijvingaantekeningkadastraalobject("beschrijvingaantekeningkadastraalobject2")
            .kadasteridentificatieaantekening("kadasteridentificatieaantekening2");
    }

    public static Kadastraleonroerendezaakaantekening getKadastraleonroerendezaakaantekeningRandomSampleGenerator() {
        return new Kadastraleonroerendezaakaantekening()
            .id(longCount.incrementAndGet())
            .aardaantekeningkadastraalobject(UUID.randomUUID().toString())
            .beschrijvingaantekeningkadastraalobject(UUID.randomUUID().toString())
            .kadasteridentificatieaantekening(UUID.randomUUID().toString());
    }
}
