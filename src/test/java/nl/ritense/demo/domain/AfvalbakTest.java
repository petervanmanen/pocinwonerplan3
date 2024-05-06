package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AfvalbakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AfvalbakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Afvalbak.class);
        Afvalbak afvalbak1 = getAfvalbakSample1();
        Afvalbak afvalbak2 = new Afvalbak();
        assertThat(afvalbak1).isNotEqualTo(afvalbak2);

        afvalbak2.setId(afvalbak1.getId());
        assertThat(afvalbak1).isEqualTo(afvalbak2);

        afvalbak2 = getAfvalbakSample2();
        assertThat(afvalbak1).isNotEqualTo(afvalbak2);
    }
}
