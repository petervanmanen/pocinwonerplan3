package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MeldingongevalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MeldingongevalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Meldingongeval.class);
        Meldingongeval meldingongeval1 = getMeldingongevalSample1();
        Meldingongeval meldingongeval2 = new Meldingongeval();
        assertThat(meldingongeval1).isNotEqualTo(meldingongeval2);

        meldingongeval2.setId(meldingongeval1.getId());
        assertThat(meldingongeval1).isEqualTo(meldingongeval2);

        meldingongeval2 = getMeldingongevalSample2();
        assertThat(meldingongeval1).isNotEqualTo(meldingongeval2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Meldingongeval meldingongeval = new Meldingongeval();
        assertThat(meldingongeval.hashCode()).isZero();

        Meldingongeval meldingongeval1 = getMeldingongevalSample1();
        meldingongeval.setId(meldingongeval1.getId());
        assertThat(meldingongeval).hasSameHashCodeAs(meldingongeval1);
    }
}
