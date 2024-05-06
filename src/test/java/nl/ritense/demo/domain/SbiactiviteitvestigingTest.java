package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SbiactiviteitvestigingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SbiactiviteitvestigingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sbiactiviteitvestiging.class);
        Sbiactiviteitvestiging sbiactiviteitvestiging1 = getSbiactiviteitvestigingSample1();
        Sbiactiviteitvestiging sbiactiviteitvestiging2 = new Sbiactiviteitvestiging();
        assertThat(sbiactiviteitvestiging1).isNotEqualTo(sbiactiviteitvestiging2);

        sbiactiviteitvestiging2.setId(sbiactiviteitvestiging1.getId());
        assertThat(sbiactiviteitvestiging1).isEqualTo(sbiactiviteitvestiging2);

        sbiactiviteitvestiging2 = getSbiactiviteitvestigingSample2();
        assertThat(sbiactiviteitvestiging1).isNotEqualTo(sbiactiviteitvestiging2);
    }
}
