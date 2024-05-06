package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DoelgroepTestSamples.*;
import static nl.ritense.demo.domain.MailingTestSamples.*;
import static nl.ritense.demo.domain.MuseumrelatieTestSamples.*;
import static nl.ritense.demo.domain.ProgrammaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MuseumrelatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Museumrelatie.class);
        Museumrelatie museumrelatie1 = getMuseumrelatieSample1();
        Museumrelatie museumrelatie2 = new Museumrelatie();
        assertThat(museumrelatie1).isNotEqualTo(museumrelatie2);

        museumrelatie2.setId(museumrelatie1.getId());
        assertThat(museumrelatie1).isEqualTo(museumrelatie2);

        museumrelatie2 = getMuseumrelatieSample2();
        assertThat(museumrelatie1).isNotEqualTo(museumrelatie2);
    }

    @Test
    void voorProgrammaTest() throws Exception {
        Museumrelatie museumrelatie = getMuseumrelatieRandomSampleGenerator();
        Programma programmaBack = getProgrammaRandomSampleGenerator();

        museumrelatie.addVoorProgramma(programmaBack);
        assertThat(museumrelatie.getVoorProgrammas()).containsOnly(programmaBack);
        assertThat(programmaBack.getVoorMuseumrelatie()).isEqualTo(museumrelatie);

        museumrelatie.removeVoorProgramma(programmaBack);
        assertThat(museumrelatie.getVoorProgrammas()).doesNotContain(programmaBack);
        assertThat(programmaBack.getVoorMuseumrelatie()).isNull();

        museumrelatie.voorProgrammas(new HashSet<>(Set.of(programmaBack)));
        assertThat(museumrelatie.getVoorProgrammas()).containsOnly(programmaBack);
        assertThat(programmaBack.getVoorMuseumrelatie()).isEqualTo(museumrelatie);

        museumrelatie.setVoorProgrammas(new HashSet<>());
        assertThat(museumrelatie.getVoorProgrammas()).doesNotContain(programmaBack);
        assertThat(programmaBack.getVoorMuseumrelatie()).isNull();
    }

    @Test
    void valtbinnenDoelgroepTest() throws Exception {
        Museumrelatie museumrelatie = getMuseumrelatieRandomSampleGenerator();
        Doelgroep doelgroepBack = getDoelgroepRandomSampleGenerator();

        museumrelatie.addValtbinnenDoelgroep(doelgroepBack);
        assertThat(museumrelatie.getValtbinnenDoelgroeps()).containsOnly(doelgroepBack);

        museumrelatie.removeValtbinnenDoelgroep(doelgroepBack);
        assertThat(museumrelatie.getValtbinnenDoelgroeps()).doesNotContain(doelgroepBack);

        museumrelatie.valtbinnenDoelgroeps(new HashSet<>(Set.of(doelgroepBack)));
        assertThat(museumrelatie.getValtbinnenDoelgroeps()).containsOnly(doelgroepBack);

        museumrelatie.setValtbinnenDoelgroeps(new HashSet<>());
        assertThat(museumrelatie.getValtbinnenDoelgroeps()).doesNotContain(doelgroepBack);
    }

    @Test
    void versturenaanMailingTest() throws Exception {
        Museumrelatie museumrelatie = getMuseumrelatieRandomSampleGenerator();
        Mailing mailingBack = getMailingRandomSampleGenerator();

        museumrelatie.addVersturenaanMailing(mailingBack);
        assertThat(museumrelatie.getVersturenaanMailings()).containsOnly(mailingBack);
        assertThat(mailingBack.getVersturenaanMuseumrelaties()).containsOnly(museumrelatie);

        museumrelatie.removeVersturenaanMailing(mailingBack);
        assertThat(museumrelatie.getVersturenaanMailings()).doesNotContain(mailingBack);
        assertThat(mailingBack.getVersturenaanMuseumrelaties()).doesNotContain(museumrelatie);

        museumrelatie.versturenaanMailings(new HashSet<>(Set.of(mailingBack)));
        assertThat(museumrelatie.getVersturenaanMailings()).containsOnly(mailingBack);
        assertThat(mailingBack.getVersturenaanMuseumrelaties()).containsOnly(museumrelatie);

        museumrelatie.setVersturenaanMailings(new HashSet<>());
        assertThat(museumrelatie.getVersturenaanMailings()).doesNotContain(mailingBack);
        assertThat(mailingBack.getVersturenaanMuseumrelaties()).doesNotContain(museumrelatie);
    }
}
