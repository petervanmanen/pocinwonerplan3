package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ZakelijkrechtTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ZakelijkrechtTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zakelijkrecht.class);
        Zakelijkrecht zakelijkrecht1 = getZakelijkrechtSample1();
        Zakelijkrecht zakelijkrecht2 = new Zakelijkrecht();
        assertThat(zakelijkrecht1).isNotEqualTo(zakelijkrecht2);

        zakelijkrecht2.setId(zakelijkrecht1.getId());
        assertThat(zakelijkrecht1).isEqualTo(zakelijkrecht2);

        zakelijkrecht2 = getZakelijkrechtSample2();
        assertThat(zakelijkrecht1).isNotEqualTo(zakelijkrecht2);
    }
}
