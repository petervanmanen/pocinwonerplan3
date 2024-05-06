package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MailingTestSamples.*;
import static nl.ritense.demo.domain.MuseumrelatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MailingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mailing.class);
        Mailing mailing1 = getMailingSample1();
        Mailing mailing2 = new Mailing();
        assertThat(mailing1).isNotEqualTo(mailing2);

        mailing2.setId(mailing1.getId());
        assertThat(mailing1).isEqualTo(mailing2);

        mailing2 = getMailingSample2();
        assertThat(mailing1).isNotEqualTo(mailing2);
    }

    @Test
    void versturenaanMuseumrelatieTest() throws Exception {
        Mailing mailing = getMailingRandomSampleGenerator();
        Museumrelatie museumrelatieBack = getMuseumrelatieRandomSampleGenerator();

        mailing.addVersturenaanMuseumrelatie(museumrelatieBack);
        assertThat(mailing.getVersturenaanMuseumrelaties()).containsOnly(museumrelatieBack);

        mailing.removeVersturenaanMuseumrelatie(museumrelatieBack);
        assertThat(mailing.getVersturenaanMuseumrelaties()).doesNotContain(museumrelatieBack);

        mailing.versturenaanMuseumrelaties(new HashSet<>(Set.of(museumrelatieBack)));
        assertThat(mailing.getVersturenaanMuseumrelaties()).containsOnly(museumrelatieBack);

        mailing.setVersturenaanMuseumrelaties(new HashSet<>());
        assertThat(mailing.getVersturenaanMuseumrelaties()).doesNotContain(museumrelatieBack);
    }
}
