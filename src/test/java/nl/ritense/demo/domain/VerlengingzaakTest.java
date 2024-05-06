package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerlengingzaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerlengingzaakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verlengingzaak.class);
        Verlengingzaak verlengingzaak1 = getVerlengingzaakSample1();
        Verlengingzaak verlengingzaak2 = new Verlengingzaak();
        assertThat(verlengingzaak1).isNotEqualTo(verlengingzaak2);

        verlengingzaak2.setId(verlengingzaak1.getId());
        assertThat(verlengingzaak1).isEqualTo(verlengingzaak2);

        verlengingzaak2 = getVerlengingzaakSample2();
        assertThat(verlengingzaak1).isNotEqualTo(verlengingzaak2);
    }
}
