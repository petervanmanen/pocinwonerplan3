package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ElogTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ElogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Elog.class);
        Elog elog1 = getElogSample1();
        Elog elog2 = new Elog();
        assertThat(elog1).isNotEqualTo(elog2);

        elog2.setId(elog1.getId());
        assertThat(elog1).isEqualTo(elog2);

        elog2 = getElogSample2();
        assertThat(elog1).isNotEqualTo(elog2);
    }
}
